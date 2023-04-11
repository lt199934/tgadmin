package com.tg.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tg.admin.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Program: admin
 * @ClassName UserMapper
 * @Author: liutao
 * @Description: 用户接口
 * @Create: 2023-03-12 07:04
 * @Version 1.0
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {
    int insertUser(User user);
    @Update("update sys_user set status = #{status} where id = #{id}")
    int delelteUser(Integer id,Integer status);
    int updateUser(User user);
    @Select("select * from sys_user")
    List<User> findAll();
    List<User> findPage(Integer pageNum,Integer pageSize,String username,String email,String address);
    Integer count(String username,String email,String address);
}
