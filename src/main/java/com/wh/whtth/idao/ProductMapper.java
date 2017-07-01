package com.wh.whtth.idao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.wh.whtth.model.Product;
import com.wh.whtth.vo.ViewVo;

@Repository("productDao")
public interface ProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
    
    Integer checkProductBySort(String sortid);
    
    //查看待审核的产品列表
    List<Product> checkPendingProducts(ViewVo vo);
    
    //上架产品列表
    List<Product> listValidProduct(ViewVo vo);

    //上架产品列表
    List<Product> listInvalidProduct(ViewVo vo);
    
    List<Map<String,String>> listValidProductById(@Param("id")String id,@Param("sortid")String sortid);
    
    List<Map<String,String>> getShopinfoBySortid(@Param("id")String id,@Param("sortid")String sortid);
}