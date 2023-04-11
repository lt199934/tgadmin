package com.tg.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tg.admin.entity.Menu;
import com.tg.admin.entity.Role;
import com.tg.admin.entity.RoleMenu;
import com.tg.admin.mapper.RoleMapper;
import com.tg.admin.mapper.RoleMenuMapper;
import com.tg.admin.service.MenuService;
import com.tg.admin.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liutao
 * @since 2023-03-19
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private MenuService menuService;

    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
        QueryWrapper<RoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
        roleMenuQueryWrapper.eq("role_id",roleId);
        roleMenuMapper.delete(roleMenuQueryWrapper);
        List<Integer> menuCopy = CollUtil.newArrayList(menuIds);
        for (Integer id: menuIds) {
            Menu menu = menuService.getById(id);
            if(menu.getPid() != null && ! menuCopy.contains(menu.getPid())) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menu.getPid());
                roleMenuMapper.insert(roleMenu);
                menuCopy.add(menu.getPid());
            }
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(id);
            roleMenuMapper.insert(roleMenu);
        }
    }

    @Override
    public List<RoleMenu> setRoleMenu(Integer roleId) {
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("menu_id");
        queryWrapper.eq("role_id",roleId);
        return roleMenuMapper.selectList(queryWrapper);
    }
}
