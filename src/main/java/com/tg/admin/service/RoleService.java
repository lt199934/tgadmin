package com.tg.admin.service;

import com.tg.admin.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tg.admin.entity.RoleMenu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liutao
 * @since 2023-03-19
 */
public interface RoleService extends IService<Role> {

    void setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<RoleMenu> setRoleMenu(Integer roleId);
}
