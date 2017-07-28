package com.wh.whtth.idao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.wh.whtth.model.City;

@Repository("cityDao")
public interface CityMapper {
    int insert(City record);

    int insertSelective(City record);

	Map<String,Object> listProvince();

	Object listCityUnderProv(@Param("code")String code);
	
	String findCityByCode(@Param("code")String code);
}