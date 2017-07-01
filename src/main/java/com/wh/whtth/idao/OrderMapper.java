package com.wh.whtth.idao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.wh.whtth.model.Order;
import com.wh.whtth.vo.ViewVo;

@Repository("orderDao")
public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
    
    List<Map<String,String>> shoppingInfo(ViewVo vo);

	List<Map<String,String>> showOrders(ViewVo vo);

	Map<String, String> SelectOrderinfoById(@Param("id")String id);

	List<Map<String, String>> getDetailsById(@Param("id")String id);
	
	int getNextval();
	
	int getAccount(@Param("orderid")String orderid);
}