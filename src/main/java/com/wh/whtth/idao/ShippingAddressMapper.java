package com.wh.whtth.idao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.wh.whtth.model.ShippingAddress;

@Repository("shippingAddressDao")
public interface ShippingAddressMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShippingAddress record);

    int insertSelective(ShippingAddress record);

    ShippingAddress selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShippingAddress record);

    int updateByPrimaryKey(ShippingAddress record);

	Map<String,Object> listShippingAddress(@Param("userid")String userid);
}