package io.code.sb3.mybatis.dbread.service;

import lombok.extern.slf4j.Slf4j;
import io.code.sb3.mybatis.dbread.mapper.DataMapper;
import io.code.sb3.mybatis.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @Author: zongz
 * @Date: 2025-03-01
 */
@Service
@Slf4j
public class AsyncService {

    @Autowired
    private DataMapper dataMapper;

    // 分页大小建议设置为数据库单次高效查询量
    private static final int PAGE_SIZE = 5000;

    @Async("taskExecutor")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CompletableFuture<Void> processPage(int offset, int limit) {
        try {
            // 业务逻辑
            List<User> userList = dataMapper.selectByPage(offset, limit);
            // 处理数据逻辑
            process(userList);
            return CompletableFuture.completedFuture(null);
        } catch (Exception e) {
            // 记录日志或进行补偿操作
            throw new RuntimeException("处理失败", e);
        }
    }

    private void process(List<User> userList) {
        // 你的数据处理逻辑
        userList.stream().forEach(user -> {
            //FileUtil.appendUtf8String(user.toString() + "\n", "D:/data.txt");
            System.out.println(user);
        });
    }

    @Async("taskExecutor")
    public CompletableFuture<Void> processRange(long startId, long endId) {
        log.info("Processing range [{}-{}]", startId, endId);


        long currentId = startId;

        while (currentId <= endId) {
            List<User> data = dataMapper.selectByCursor(currentId, PAGE_SIZE);

            // 终止条件：没有数据或超出当前范围
            if (data.isEmpty() || data.get(data.size() - 1).getId() > endId) {
                break;
            }

            processData(data); // 处理数据
            currentId = data.get(data.size() - 1).getId() + 1; // 更新游标
        }
        log.info("Completed range [{}-{}]", startId, endId);

        return CompletableFuture.completedFuture(null);
    }

    private void processData(List<User> userList) {
        // 业务处理逻辑
        userList.stream().forEach(user -> {
            //FileUtil.appendUtf8String(user.toString() + "\n", "D:/data.txt");
            System.out.println(user);
        });
    }

}
