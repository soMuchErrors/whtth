package com.wh.whtth.idao;

import org.springframework.stereotype.Repository;

import com.wh.whtth.model.BrowseDetails;

@Repository("browseDetailsDao")
public interface BrowseDetailsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BrowseDetails record);

    int insertSelective(BrowseDetails record);

    BrowseDetails selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BrowseDetails record);

    int updateByPrimaryKey(BrowseDetails record);
}