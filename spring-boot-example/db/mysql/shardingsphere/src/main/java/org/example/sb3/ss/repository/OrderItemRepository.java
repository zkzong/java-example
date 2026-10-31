package org.example.sb3.ss.repository;

import org.apache.ibatis.annotations.Mapper;
import org.example.sb3.ss.entity.OrderItem;

import java.util.List;

@Mapper
public interface OrderItemRepository {

    void createTableIfNotExists();

    void truncateTable();

    void dropTable();

    void insert(OrderItem orderItem);

    void delete(long orderId);

    List<OrderItem> selectAll();
}
