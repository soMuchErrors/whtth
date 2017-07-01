package com.wh.whtth.idao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.wh.whtth.model.ShoppingCart;

@Repository("shoppingCartDao")
public interface ShoppingCartMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShoppingCart record);

    int insertSelective(ShoppingCart record);

    ShoppingCart selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShoppingCart record);

    int updateByPrimaryKey(ShoppingCart record);
    
    //查看用户在具体某个店的购物车信息
    List<Map<String,String>> shoppingCartInThis(@Param("userid") String userid,@Param("shopid") String shopid);
    
    //查看用户购物车信息
    List<Map<String,String>> privateShoppingCart(@Param("userid") String userid);
    
    int getNextval();
}