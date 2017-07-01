package com.wh.whtth.idao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wh.whtth.model.Shop;
import com.wh.whtth.vo.ShopManageVo;
import com.wh.whtth.vo.ViewVo;

@Repository("shopDao")
public interface ShopMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Shop record);

    int insertSelective(Shop record);

    Shop selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Shop record);

    int updateByPrimaryKey(Shop record);
    
    List<ShopManageVo> view(ViewVo vo);
    
    List<ShopManageVo> viewById(ViewVo vo);
    
    List<Shop> listValidShops(ViewVo vo);
    List<Shop> listInvalidShops(ViewVo vo);

	List<Map<String,String>> showShops(ViewVo vo);
	
}