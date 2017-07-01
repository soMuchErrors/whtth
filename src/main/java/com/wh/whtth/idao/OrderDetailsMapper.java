package com.wh.whtth.idao;

import org.springframework.stereotype.Repository;

import com.wh.whtth.model.OrderDetails;

@Repository("orderDetailsDao")
public interface OrderDetailsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderDetails record);

    int insertSelective(OrderDetails record);

    OrderDetails selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderDetails record);

    int updateByPrimaryKey(OrderDetails record);
}