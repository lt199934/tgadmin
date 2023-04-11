package com.tg.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.admin.common.Constants;
import com.tg.admin.common.exception.ServiceException;
import com.tg.admin.dto.UserDto;
import com.tg.admin.entity.User;
import com.tg.admin.mapper.UserMapper;
import com.tg.admin.service.UserService;
import com.tg.admin.utils.JwtUtil;
import com.tg.admin.utils.MenuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Program: admin
 * @ClassName UserServiceImpl
 * @Author: liutao
 * @Description: 用户逻辑接口实现类
 * @Create: 2023-03-12 07:46
 * @Version 1.0
 **/

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>  implements UserService  {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuUtil menuUtil;

    private static  final Log log = Log.get();


    @Override
    public boolean saveOrUpdate(User user) {
        return super.saveOrUpdate(user);
    }
    @Override
    public int saveUser(User user) {
        if(user.getId() == null){
            return userMapper.insert(user);
        }else {
            return userMapper.updateUser(user);
        }
    }


    @Override
    public int delelteUser(Integer id,Integer status) {
        return userMapper.delelteUser(id,status);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public List<User> findPage(Integer pageNum, Integer pageSize,String username,String email,String address) {
        return userMapper.findPage(pageNum,pageSize,username,email,address);
    }

    @Override
    public Integer userCount(String username,String email,String address) {
        return userMapper.count(username,email,address);
    }

    @Override
    public UserDto login(UserDto userDto) {
        User one = getUserInfo(userDto);
        if (one != null) {
            if(one.getOnlineStatus() == false) {
                one.setOnlineStatus(true);
                System.out.println(one);
                updateById(one);
            }
            BeanUtil.copyProperties(one, userDto, true);
            String token = JwtUtil.getToken(one.getId().toString(), one.getPassword());
            userDto.setToken(token);
//            查出用户角色
            String role = one.getRole();
            userDto.setMenus(menuUtil.getRoleMenus(role));
            return userDto;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }

    @Override
    public User register(UserDto userDto) {
      User one = getUserInfo(userDto);
        if(one == null) {
            one = new User();
            BeanUtil.copyProperties(userDto, one, true);
            save(one);

        }else {
            throw new ServiceException(Constants.CODE_500, "用户已存在");
        }
        return one;
    }

    private User getUserInfo(UserDto userDto){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDto.getUsername());
        queryWrapper.eq("password", userDto.getPassword());
        User one;
        try {
            one = getOne(queryWrapper);
            return one;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
    }

}
