package com.tg.admin.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @Program: admin
 * @ClassName UserVo
 * @Author: liutao
 * @Description: 接收前端请求
 * @Create: 2023-04-09 19:18
 * @Version 1.0
 **/
@ApiModel("接收前端请求")
@Data
public class UserVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private String code;
}
