package com.tg.admin.service;

import com.tg.admin.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liutao
 * @since 2023-03-19
 */
public interface MenuService extends IService<Menu> {

    List<Menu> findMenus(String name);
}
