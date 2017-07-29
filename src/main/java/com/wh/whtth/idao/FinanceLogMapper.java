package com.wh.whtth.idao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
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

	List<Map<String,Object>> consumptionStatus(@Param("userid") String userid,@Param("ym") String ym);

	Map<String,Object> consumptionAnalyze(@Param("userid") String userid,@Param("ym") String ym);

	List<Map<String,Object>> consumptionDetail(@Param("userid")String userid,@Param("ym") String ym,@Param("page") int page,@Param("pagesize") int pagesize);
}