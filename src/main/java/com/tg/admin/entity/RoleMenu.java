package com.tg.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Program: admin
 * @ClassName RoleMenu
 * @Author: liutao
 * @Description:
 * @Create: 2023-03-20 02:04
 * @Version 1.0
 **/

@Data
@TableName("sys_role_menu")
public class RoleMenu {
    private Integer roleId;
    private  Integer menuId;
}
