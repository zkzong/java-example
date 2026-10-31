package org.example.sb3.ss.repository;

import org.example.sb3.ss.entity.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void insert() {
        Order order = new Order();
        order.setOrderId(1);
        order.setUserId(1);
        order.setOrderType(1 % 2);
        order.setAddressId(1);
        order.setStatus("INSERT_TEST");
        orderRepository.insert(order);
    }

    @Test
    public void delete() {
        orderRepository.delete(1L);
    }

    @Test
    public void selectAll() {
        List<Order> orders = orderRepository.selectAll();
        System.out.println(orders);
    }
}
