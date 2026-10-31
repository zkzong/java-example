package com.example.es.dao;

import com.example.es.entity.Commodity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommodityRepository extends ElasticsearchRepository<Commodity, String> {

    List<Commodity> findByName(String name);

    @Query("{\"match\": {\"name\": {\"query\": \"?0\"}}}")
    Page<Commodity> pageByName(String name, Pageable pageable);

}
