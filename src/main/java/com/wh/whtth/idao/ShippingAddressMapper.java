package com.wh.whtth.idao;

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
}