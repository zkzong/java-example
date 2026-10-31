package com.example.es.service.impl;

import com.example.es.dao.CommodityRepository;
import com.example.es.entity.Commodity;
import com.example.es.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    private CommodityRepository commodityRepository;

    @Override
    public long count() {
        return commodityRepository.count();
    }

    @Override
    public Commodity save(Commodity commodity) {
        return commodityRepository.save(commodity);
    }

    @Override
    public void delete(Commodity commodity) {
        commodityRepository.delete(commodity);
        //commodityRepository.deleteById(commodity.getSkuId());
    }

    @Override
    public Iterable<Commodity> getAll() {
        return commodityRepository.findAll();
    }

    @Override
    public List<Commodity> findByName(String name) {
        List<Commodity> list = commodityRepository.findByName(name);
        return list;
    }

    @Override
    public Page<Commodity> pageQuery(Integer pageNo, Integer pageSize, String kw) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        // searchSimilar默认只能使用ID进行匹配查询
        Page<Commodity> page = commodityRepository.findById(kw)
                .map(commodity -> commodityRepository.searchSimilar(commodity, new String[]{"skuId"}, pageable))
                .orElse(Page.empty());
        return page;
    }

    @Override
    public Page<Commodity> pageByName(Integer pageNo, Integer pageSize, String name) {
        return commodityRepository.pageByName(name, PageRequest.of(pageNo, pageSize));
    }


}
