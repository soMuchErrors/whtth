package com.wh.whtth.idao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.wh.whtth.model.User;
import com.wh.whtth.vo.ViewVo;

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
    
    int selectByUsername(@Param("username")String username);
    
    Map<String, Object> login(@Param("username")String username,@Param("password") String password);

	List<Map<String, String>> listValidUser(ViewVo vo);

	int countValidUser();

	List<Map<String, String>> listInvalidUser(ViewVo vo);

	int countInvalidUser();
}