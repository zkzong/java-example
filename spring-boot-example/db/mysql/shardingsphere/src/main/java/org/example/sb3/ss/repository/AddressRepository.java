package org.example.sb3.ss.repository;

import org.apache.ibatis.annotations.Mapper;
import org.example.sb3.ss.entity.Address;

import java.util.List;

@Mapper
public interface AddressRepository {

    void createTableIfNotExists();

    void truncateTable();

    void dropTable();

    void insert(Address address);

    void delete(long id);

    List<Address> selectAll();
}
