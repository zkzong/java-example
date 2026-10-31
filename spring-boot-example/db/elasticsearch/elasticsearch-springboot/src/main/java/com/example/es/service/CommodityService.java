package com.example.es.service;

import com.example.es.entity.Commodity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommodityService {

    long count();

    Commodity save(Commodity commodity);

    void delete(Commodity commodity);

    Iterable<Commodity> getAll();

    List<Commodity> findByName(String name);

    Page<Commodity> pageQuery(Integer pageNo, Integer pageSize, String kw);

    Page<Commodity> pageByName(Integer pageNo, Integer pageSize, String name);

}
