package com.wh.whtth.idao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wh.whtth.model.BaseCode;
@Repository("baseCodeDao")
public interface BaseCodeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BaseCode record);

    int insertSelective(BaseCode record);

    BaseCode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseCode record);

    int updateByPrimaryKey(BaseCode record);

	List<Map<String,String>> showTrade();
}