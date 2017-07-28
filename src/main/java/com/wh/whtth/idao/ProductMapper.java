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
    List<Map<String,String>> checkPendingProducts(ViewVo vo);
    
    int countPendingProducts();
    
    //上架产品列表
    List<Map<String,String>> listValidProduct(ViewVo vo);
    
    int countValidProduct(@Param("id")String id);
    
    //上架产品列表
    List<Map<String, String>> listInvalidProduct(ViewVo vo);
    
    int countInvalidProduct(@Param("id")String id);
    
    List<Map<String,String>> listValidProductById(@Param("id")String id,@Param("sortid")String sortid);
    
    int countlistValidProductById(@Param("id")String id,@Param("sortid")String sortid);
    
    List<Map<String,String>> getShopinfoBySortid(@Param("id")String id,@Param("sortid")String sortid);

	

	
}