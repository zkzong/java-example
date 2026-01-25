package io.code.sb3.mybatis.service;

import io.code.sb3.mybatis.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 多线程读取数据库服务接口
 */
public interface MultiThreadReadService {

    /**
     * 使用100个线程并发读取t_user表数据
     * @return 读取结果统计信息（线程数、总记录数、耗时等）
     */
    Map<String, Object> readWithMultiThreads();

    /**
     * 使用指定数量的线程并发读取t_user表数据
     * @param threadCount 线程数量
     * @return 读取结果统计信息
     */
    Map<String, Object> readWithMultiThreads(int threadCount);

    /**
     * 使用固定100个线程并发读取，返回所有线程读取的结果列表
     * @return 所有线程的读取结果
     */
    List<User> readUsersWithMultiThreads();

}
