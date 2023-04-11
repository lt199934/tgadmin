package com.tg.admin.dto;

import com.tg.admin.entity.Menu;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Program: admin
 * @ClassName UserDto
 * @Author: liutao
 * @Description: 业务视图接收前端登录请求,返回响应视图
 * @Create: 2023-03-14 02:56
 * @Version 1.0
 **/
@ApiModel("用户数据传输和封装返回")
@Data
public class UserDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
    private String role;
    private String code;
    private List<Menu> menus;
}
