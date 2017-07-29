package com.wh.whtth.idao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
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
   
    Shop selectByUserid(@Param("userid") Long userid);
    
    int updateByPrimaryKeySelective(Shop record);

    int updateByPrimaryKey(Shop record);
    
    List<ShopManageVo> view(ViewVo vo);
    
    List<ShopManageVo> viewById(ViewVo vo);
    
    List<Map<String,String>> listValidShops(ViewVo vo);
    
    int countValidShops();
    
    List<Map<String,String>> listInvalidShops(ViewVo vo);
    
    int countInvalidShops();

	List<Map<String,String>> showShops(ViewVo vo);

	Map<String,String> showinfo(String id);

}