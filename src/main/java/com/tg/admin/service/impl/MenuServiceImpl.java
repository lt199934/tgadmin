package com.tg.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tg.admin.entity.Menu;
import com.tg.admin.mapper.MenuMapper;
import com.tg.admin.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liutao
 * @since 2023-03-19
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<Menu> findMenus(String name) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)) {
            queryWrapper.like("name", name);
        }
        List<Menu> list = list(queryWrapper);
//        一级菜单
        List<Menu> parent = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        System.out.println(parent);
        for (Menu menu:parent){
//            一级菜单子菜单
            menu.setChildren(list.stream().filter(m ->menu.getId().equals(m.getPid())).collect(Collectors.toList()));
//            二级子菜单
//            for (Menu me:menu.getChildren()) {
//                me.setChildren(list.stream().filter(m ->me.getId().equals(m.getPid())).collect(Collectors.toList()));
//            }

        }
        return parent;
    }
}
