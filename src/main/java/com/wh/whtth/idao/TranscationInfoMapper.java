package com.wh.whtth.idao;

import org.springframework.stereotype.Repository;

import com.wh.whtth.model.TranscationInfo;

@Repository("transcationInfoDao")
public interface TranscationInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TranscationInfo record);

    int insertSelective(TranscationInfo record);

    TranscationInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TranscationInfo record);

    int updateByPrimaryKey(TranscationInfo record);
}