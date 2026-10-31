package com.example.es.service;

import com.example.es.entity.Commodity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ElasticsearchTemplateTest {

    @Autowired
    public ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void testInsert() {
        Commodity commodity = new Commodity();
        commodity.setSkuId("1501009005");
        commodity.setName("葡萄吐司面包（10片装）");
        commodity.setCategory("101");
        commodity.setPrice(160);
        commodity.setBrand("良品铺子");
        elasticsearchTemplate.save(commodity);

        //IndexQuery indexQuery = new IndexQueryBuilder().withObject(commodity).build();

    }

    @Test
    public void testQuery() {
        String name = "面包";
        Query query = new StringQuery(
                "{ \"match\": { \"name\": { \"query\": \"" + name + " \" } } } ");
        IndexCoordinates indexCoordinates = IndexCoordinates.of("name");
        SearchHits<Commodity> searchHits = elasticsearchTemplate.search(query, Commodity.class);
        searchHits.stream().forEach(System.out::println);
    }

}
