package com.tg.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tg.admin.dto.UserDto;
import com.tg.admin.entity.User;

import java.util.List;

/**
 * @Program: admin
 * @ClassName UserService
 * @Author: liutao
 * @Description: 用户业务逻辑接口
 * @Create: 2023-03-12 07:45
 * @Version 1.0
 **/


public interface UserService extends IService<User> {
    int saveUser( User user);
    int delelteUser(Integer id,Integer status);
    List<User> findAll();
    List<User> findPage(Integer pageNum,Integer pageSize,String username,String email,String address);
    Integer userCount(String username,String email,String address);

    UserDto login(UserDto userDto);

    User register(UserDto userDto);
}
