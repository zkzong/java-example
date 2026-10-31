package org.example.sb3.ss.repository;

import org.apache.ibatis.annotations.Mapper;
import org.example.sb3.ss.entity.Order;

import java.util.List;

@Mapper
public interface OrderRepository {

    void createTableIfNotExists();

    void truncateTable();

    void dropTable();

    void insert(Order order);

    void delete(long orderId);

    List<Order> selectAll();
}
