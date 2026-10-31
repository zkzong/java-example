package io.code.sb3.mybatis.dbread.service;

import io.code.sb3.mybatis.dbread.mapper.DataMapper;
import io.code.sb3.mybatis.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @Author: zongz
 * @Date: 2025-03-01
 */
@Service
public class DataService {

    @Autowired
    private DataMapper dataMapper;

    @Autowired
    private AsyncService asyncService;

    // 分页大小
    private static final int PAGE_SIZE = 1000;

    // 每个线程建议处理的数据量（根据测试调整）
    private static final long PER_THREAD_ROWS = 100_000;

    public void processData() {
        int total = dataMapper.totalCount();
        int totalPages = (int) Math.ceil((double) total / PAGE_SIZE);

        for (int page = 0; page < totalPages; page++) {
            int offset = page * PAGE_SIZE;
            List<User> userList = dataMapper.selectByPage(offset, PAGE_SIZE);
            userList.stream().forEach(user -> {
                //FileUtil.appendUtf8String(user.toString() + "\n", "D:/data.txt");
                System.out.println(user);
            });
        }

    }

    public void processDataInParallel() {
        int total = dataMapper.totalCount();
        int totalPages = (int) Math.ceil((double) total / PAGE_SIZE);

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // 创建并提交任务
        for (int page = 0; page < totalPages; page++) {
            int offset = page * PAGE_SIZE;
            futures.add(asyncService.processPage(offset, PAGE_SIZE));
        }

        // 等待所有任务完成
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    public void processDataInParallelCursor() {
        long minId = dataMapper.selectMinId();
        long maxId = dataMapper.selectMaxId();

        // 动态计算线程数量
        int threadCount = (int) Math.ceil((double) (maxId - minId) / PER_THREAD_ROWS);

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // 创建分区任务
        for (int i = 0; i < threadCount; i++) {
            long startId = minId + (i * PER_THREAD_ROWS);
            long endId = Math.min(startId + PER_THREAD_ROWS - 1, maxId);
            futures.add(asyncService.processRange(startId, endId));
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    // 动态调整分区的服务实现
    public void dynamicProcess() {
        long currentId = dataMapper.selectMinId();
        final long maxId = dataMapper.selectMaxId();

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        while (currentId <= maxId) {
            final long batchStart = currentId;
            final long batchEnd = Math.min(batchStart + PER_THREAD_ROWS, maxId);

            futures.add(asyncService.processRange(batchStart, batchEnd));
            currentId = batchEnd + 1;
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }
}
