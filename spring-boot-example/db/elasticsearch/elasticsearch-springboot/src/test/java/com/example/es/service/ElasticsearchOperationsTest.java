package com.example.es.service;

import com.example.es.entity.Commodity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ElasticsearchOperationsTest {

    @Autowired
    public ElasticsearchOperations elasticsearchOperations;

    @Test
    public void save() {
        Commodity commodity = new Commodity();
        commodity.setSkuId("1501009006");
        commodity.setName("葡萄吐司面包（10片装）");
        commodity.setCategory("101");
        commodity.setPrice(160);
        commodity.setBrand("良品铺子");
        elasticsearchOperations.save(commodity);
    }

    @Test
    public void delete() {
        elasticsearchOperations.delete("1501009006", Commodity.class);
    }

    @Test
    public void get() {
        Commodity commodity = elasticsearchOperations.get("1501009006", Commodity.class);
        System.out.println(commodity);
    }

    @Test
    public void stringQuery() {
        String name = "面包";
        Query query = new StringQuery(
                "{ \"match\": { \"name\": { \"query\": \"" + name + " \" } } } ");
        SearchHits<Commodity> searchHits = elasticsearchOperations.search(query, Commodity.class);
        System.out.println(searchHits.stream().collect(Collectors.toList()));

        List<Commodity> list = searchHits.getSearchHits().stream().map(SearchHit::getContent).toList();
        System.out.println(list);
        searchHits.getSearchHits().stream().map(SearchHit::getContent).forEach(System.out::println);
    }

    @Test
    public void nativeQuery() {
        String name = "面包";
        Query query = NativeQuery.builder()
                .withQuery(q -> q.match(m -> m.field("name").query(name))).build();
        SearchHits<Commodity> searchHits = elasticsearchOperations.search(query, Commodity.class);
        System.out.println(searchHits.stream().collect(Collectors.toList()));
    }


    @Test
    public void criteria() {
        Criteria criteria = new Criteria("name").is("面包");
        Query searchQuery = new CriteriaQuery(criteria);
        SearchHits<Commodity> searchHits = elasticsearchOperations.search(searchQuery, Commodity.class);
        System.out.println(searchHits.stream().collect(Collectors.toList()));

        criteria = new Criteria("price").greaterThan(100).lessThan(200);
        searchQuery = new CriteriaQuery(criteria);
        searchHits = elasticsearchOperations.search(searchQuery, Commodity.class);
        System.out.println(searchHits.stream().count());
    }


}
