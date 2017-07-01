package com.wh.whtth.idao;

import org.springframework.stereotype.Repository;

import com.wh.whtth.model.User;

@Repository("userDao")
public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);
    
    User selectByPhonenum(String phone);
    
    User selectByIdcard(String idcard);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}