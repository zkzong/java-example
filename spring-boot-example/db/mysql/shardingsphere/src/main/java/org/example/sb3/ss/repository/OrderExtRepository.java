package org.example.sb3.ss.repository;

import org.apache.ibatis.annotations.Mapper;
import org.example.sb3.ss.entity.Order;

import java.util.List;

@Mapper
public interface OrderExtRepository {

    List<Order> selectRange();

    List<Order> selecteq();

    List<Order> selectgt();

    List<Order> selectlt();

    List<Order> selectIn();

    List<Order> selectMod();

    List<Order> selectByAddress();

}
