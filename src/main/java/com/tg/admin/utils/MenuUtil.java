package com.tg.admin.utils;

import com.tg.admin.entity.Menu;
import com.tg.admin.mapper.RoleMapper;
import com.tg.admin.mapper.RoleMenuMapper;
import com.tg.admin.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Program: admin
 * @ClassName MenuUtil
 * @Author: liutao
 * @Description: 权限菜单工具
 * @Create: 2023-03-24 23:58
 * @Version 1.0
 **/

@Component
public class MenuUtil {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private MenuService menuService;
    public List<Menu> getRoleMenus(String role){
        //            获取角色id
        Integer roleId = roleMapper.selectOneByFlag(role);
//        获取权限菜单id集合
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);
        List<Menu> menus = menuService.findMenus("");
//            过滤权限菜单
        List<Menu> roleMenus = new ArrayList<>();
        for(Menu menu:menus){
            if(menuIds.contains(menu.getId())) {
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            children.removeIf(child -> !menuIds.contains(child.getId()));
        }
        return roleMenus;
    }


}
