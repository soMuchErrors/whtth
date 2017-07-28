package com.wh.whtth.idao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wh.whtth.model.FinanceLog;

@Repository("financeLogDao")
public interface FinanceLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FinanceLog record);

    int insertSelective(FinanceLog record);

    FinanceLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FinanceLog record);

    int updateByPrimaryKey(FinanceLog record);

	List<Map<String,Object>> consumptionStatus(String userid, String ym);
}