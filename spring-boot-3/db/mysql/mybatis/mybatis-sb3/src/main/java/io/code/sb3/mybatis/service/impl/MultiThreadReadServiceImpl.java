package io.code.sb3.mybatis.service.impl;

import io.code.sb3.mybatis.domain.User;
import io.code.sb3.mybatis.mapper.UserMapper;
import io.code.sb3.mybatis.service.MultiThreadReadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程读取数据库服务实现 - 使用ID范围分片（性能优化）
 *
 * @author Claude
 */
@Service
public class MultiThreadReadServiceImpl implements MultiThreadReadService {

    private static final Logger log = LoggerFactory.getLogger(MultiThreadReadServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    /**
     * 使用100个线程并发读取t_user表数据
     */
    @Override
    public Map<String, Object> readWithMultiThreads() {
        return readWithMultiThreads(100);
    }

    /**
     * 使用指定数量的线程并发读取t_user表数据
     * 使用ID范围分片，避免大offset性能问题
     */
    @Override
    public Map<String, Object> readWithMultiThreads(int threadCount) {
        long startTime = System.currentTimeMillis();

        // 1. 获取ID范围（使用主键索引，性能高）
        Long minId = userMapper.selectMinId();
        Long maxId = userMapper.selectMaxId();

        if (minId == null || maxId == null) {
            log.warn("表中没有数据");
            return createEmptyResult(threadCount, 0);
        }

        long totalCount = userMapper.count();
        log.info("表t_user: 总记录数={}, ID范围=[{}, {}]", totalCount, minId, maxId);

        // 2. 计算每个线程负责的ID范围
        long idRange = maxId - minId + 1;
        long rangePerThread = (idRange + threadCount - 1) / threadCount; // 向上取整

        // 创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                threadCount,
                threadCount,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                new ThreadFactory() {
                    private final AtomicInteger threadNumber = new AtomicInteger(1);

                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r, "MultiThreadRead-" + threadNumber.getAndIncrement());
                        t.setDaemon(false);
                        return t;
                    }
                },
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        // 用于收集结果
        List<Future<List<User>>> futures = new ArrayList<>();
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);
        AtomicInteger actualTotalRecords = new AtomicInteger(0);

        // 3. 提交任务，每个线程负责一个ID范围
        for (int i = 0; i < threadCount; i++) {
            final int threadIndex = i + 1;
            final long threadMinId = minId + i * rangePerThread;
            final long threadMaxId = Math.min(threadMinId + rangePerThread - 1, maxId);

            // 如果已经超过最大ID，就不创建任务了
            if (threadMinId > maxId) {
                break;
            }

            Future<List<User>> future = executor.submit(() -> {
                try {
                    log.info("线程-{} 开始读取: ID范围=[{}, {}]", threadIndex, threadMinId, threadMaxId);
                    List<User> users = userMapper.selectByIdRange(threadMinId, threadMaxId);
                    successCount.incrementAndGet();
                    actualTotalRecords.addAndGet(users.size());
                    log.info("线程-{} 读取完成，获取 {} 条记录", threadIndex, users.size());
                    return users;
                } catch (Exception e) {
                    failCount.incrementAndGet();
                    log.error("线程-{} 读取失败: {}", threadIndex, e.getMessage(), e);
                    return Collections.emptyList();
                }
            });
            futures.add(future);
        }

        // 4. 收集所有结果
        List<List<User>> allResults = new ArrayList<>();
        for (Future<List<User>> future : futures) {
            try {
                List<User> result = future.get();
                allResults.add(result);
            } catch (InterruptedException | ExecutionException e) {
                log.error("获取任务结果失败: {}", e.getMessage(), e);
            }
        }

        // 关闭线程池
        executor.shutdown();
        try {
            if (!executor.awaitTermination(300, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // 构建返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("totalCount", totalCount);
        result.put("idRange", String.format("[%d, %d]", minId, maxId));
        result.put("threadCount", threadCount);
        result.put("activeThreadCount", futures.size());
        result.put("rangePerThread", rangePerThread);
        result.put("successCount", successCount.get());
        result.put("failCount", failCount.get());
        result.put("totalRecords", actualTotalRecords.get());
        result.put("recordsPerThread", allResults.stream().map(List::size).toList());
        result.put("durationMs", duration);
        result.put("durationSeconds", String.format("%.2f", duration / 1000.0));
        result.put("timestamp", new Date());

        log.info("多线程ID范围分片读取完成: 总记录数={}, ID范围=[{}, {}], 线程数={}, 成功={}, 失败={}, 耗时={}ms",
                totalCount, minId, maxId, threadCount, successCount.get(), failCount.get(), duration);

        return result;
    }

    /**
     * 使用固定100个线程并发读取，返回所有线程读取的结果列表
     */
    @Override
    public List<User> readUsersWithMultiThreads() {
        return readUsersWithMultiThreads(100);
    }

    /**
     * 使用指定数量的线程并发读取，返回所有线程读取的结果列表
     */
    private List<User> readUsersWithMultiThreads(int threadCount) {
        // 1. 获取ID范围
        Long minId = userMapper.selectMinId();
        Long maxId = userMapper.selectMaxId();

        if (minId == null || maxId == null) {
            return Collections.emptyList();
        }

        // 2. 计算每个线程负责的ID范围
        long idRange = maxId - minId + 1;
        long rangePerThread = (idRange + threadCount - 1) / threadCount;

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        try {
            List<Future<List<User>>> futures = new ArrayList<>();

            // 3. 提交任务，每个线程负责一个ID范围
            for (int i = 0; i < threadCount; i++) {
                final long threadMinId = minId + i * rangePerThread;
                final long threadMaxId = Math.min(threadMinId + rangePerThread - 1, maxId);
                final int threadIndex = i + 1;

                // 如果已经超过最大ID，就不创建任务了
                if (threadMinId > maxId) {
                    break;
                }

                Future<List<User>> future = executor.submit(() -> {
                    try {
                        log.info("线程-{} 读取: ID范围=[{}, {}]", threadIndex, threadMinId, threadMaxId);
                        return userMapper.selectByIdRange(threadMinId, threadMaxId);
                    } catch (Exception e) {
                        log.error("线程-{} 读取失败: {}", threadIndex, e.getMessage(), e);
                        return Collections.emptyList();
                    }
                });
                futures.add(future);
            }

            // 4. 收集所有结果到一个列表
            List<User> allUsers = new ArrayList<>();
            for (Future<List<User>> future : futures) {
                try {
                    allUsers.addAll(future.get());
                } catch (InterruptedException | ExecutionException e) {
                    log.error("获取任务结果失败: {}", e.getMessage(), e);
                }
            }

            // 按ID排序（因为多线程执行顺序不确定）
            allUsers.sort(Comparator.comparing(User::getId));

            log.info("多线程读取完成: 共 {} 条记录", allUsers.size());
            return allUsers;

        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * 创建空结果
     */
    private Map<String, Object> createEmptyResult(int threadCount, long totalCount) {
        Map<String, Object> result = new HashMap<>();
        result.put("totalCount", totalCount);
        result.put("threadCount", threadCount);
        result.put("activeThreadCount", 0);
        result.put("successCount", 0);
        result.put("failCount", 0);
        result.put("totalRecords", 0);
        result.put("recordsPerThread", Collections.emptyList());
        result.put("durationMs", 0);
        result.put("durationSeconds", "0.00");
        result.put("timestamp", new Date());
        return result;
    }

}
