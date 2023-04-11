package com.tg.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * @Program: admin
 * @ClassName User
 * @Author: liutao
 * @Description: 系统用户类
 * @Create: 2023-03-12 06:58
 * @Version 1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("sys_user")
@ApiModel("用户表")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type= IdType.AUTO)
    @ApiModelProperty("用户id")
    private Integer id;
    private String username;
    @JsonIgnore
    private String password;
    private String nickname;
    private String avatarUrl;
    private String role;
    private Integer sex;
    private Integer age;
    private String email;
    private String phone;
    private String address;
    private Boolean onlineStatus;
    private Integer status;
    @TableField("create_time")
    private Timestamp createTime;
}
