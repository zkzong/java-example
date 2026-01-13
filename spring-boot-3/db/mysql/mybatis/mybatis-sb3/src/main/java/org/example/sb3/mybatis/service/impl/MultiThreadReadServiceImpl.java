package org.example.sb3.mybatis.service.impl;

import org.example.sb3.mybatis.domain.User;
import org.example.sb3.mybatis.mapper.UserMapper;
import org.example.sb3.mybatis.service.MultiThreadReadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程读取数据库服务实现 - 每个线程读取不同记录
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
     * 使用指定数量的线程并发读取t_user表数据 - 每个线程读取不同记录
     */
    @Override
    public Map<String, Object> readWithMultiThreads(int threadCount) {
        long startTime = System.currentTimeMillis();

        // 1. 获取总记录数
        long totalCount = userMapper.count();
        log.info("表t_user总记录数: {}", totalCount);

        if (totalCount == 0) {
            return createEmptyResult(threadCount, 0);
        }

        // 2. 计算每个线程应该读取的记录数
        int perThreadSize = (int) ((totalCount + threadCount - 1) / threadCount); // 向上取整

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

        // 3. 提交任务到线程池，每个线程读取不同的数据段
        for (int i = 0; i < threadCount; i++) {
            final int threadIndex = i + 1;
            final int offset = i * perThreadSize;
            final int limit = perThreadSize;

            // 如果offset已经超过总记录数，就不创建任务了
            if (offset >= totalCount) {
                break;
            }

            Future<List<User>> future = executor.submit(() -> {
                try {
                    log.info("线程-{} 开始读取数据: offset={}, limit={}", threadIndex, offset, limit);
                    List<User> users = userMapper.selectByPage(offset, limit);
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
        result.put("threadCount", threadCount);
        result.put("activeThreadCount", futures.size());
        result.put("perThreadSize", perThreadSize);
        result.put("successCount", successCount.get());
        result.put("failCount", failCount.get());
        result.put("totalRecords", actualTotalRecords.get());
        result.put("recordsPerThread", allResults.stream().map(List::size).toList());
        result.put("durationMs", duration);
        result.put("durationSeconds", String.format("%.2f", duration / 1000.0));
        result.put("timestamp", new Date());

        log.info("多线程分片读取完成: 总记录数={}, 线程数={}, 成功={}, 失败={}, 耗时={}ms",
                totalCount, threadCount, successCount.get(), failCount.get(), duration);

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
        // 1. 获取总记录数
        long totalCount = userMapper.count();
        if (totalCount == 0) {
            return Collections.emptyList();
        }

        // 2. 计算每个线程应该读取的记录数
        int perThreadSize = (int) ((totalCount + threadCount - 1) / threadCount);

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        try {
            List<Future<List<User>>> futures = new ArrayList<>();

            // 3. 提交任务，每个线程读取不同的数据段
            for (int i = 0; i < threadCount; i++) {
                final int offset = i * perThreadSize;

                // 如果offset已经超过总记录数，就不创建任务了
                if (offset >= totalCount) {
                    break;
                }

                final int limit = perThreadSize;
                final int threadIndex = i + 1;

                Future<List<User>> future = executor.submit(() -> {
                    try {
                        log.info("线程-{} 读取: offset={}, limit={}", threadIndex, offset, limit);
                        return userMapper.selectByPage(offset, limit);
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
