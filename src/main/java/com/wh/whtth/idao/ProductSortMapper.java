package com.wh.whtth.idao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wh.whtth.model.ProductSort;

@Repository("productSortDao")
public interface ProductSortMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSort record);

    int insertSelective(ProductSort record);

    ProductSort selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSort record);

    int updateByPrimaryKey(ProductSort record);
    
    List<ProductSort> listProductSort(String id);
}