package com.tg.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tg.admin.common.Result;
import com.tg.admin.entity.Menu;
import com.tg.admin.entity.User;
import com.tg.admin.service.MenuService;
import com.tg.admin.utils.JwtUtil;
import com.tg.admin.utils.MenuUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author liutao
 * @since 2023-03-19
 */
@Api(tags = "菜单接口" )
@RestController
@RequestMapping("/menu" )
public class MenuController {
    @Autowired
    private MenuService menuService;
    @Resource
    private MenuUtil menuUtil;

    @ApiOperation(value = "更新或新增" , httpMethod = "POST" )
    @PostMapping
    public Result<Menu> save(@RequestBody Menu menu) {
        return Result.success(menuService.saveOrUpdate(menu));
    }


    @ApiOperation("根据id删除角色" )
    @DeleteMapping("/{id}" )
    public Result<Menu> delete(@PathVariable Integer id) {
        return Result.success(menuService.removeById(id));
    }


    @ApiOperation("根据id批量删除文件" )
    @PostMapping("del/batch" )
    public Result<Menu> deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(menuService.removeBatchByIds(ids));
    }

    @GetMapping("/ids" )
    public Result<Menu> findAllIds() {
        return Result.success(menuService.list().stream().map(Menu::getId));
    }

    @ApiOperation("查询所有菜单信息" )
    @GetMapping
    public Result<Menu> findAll(@RequestParam String name) {
        return Result.success(menuService.findMenus(name));
    }

    @ApiOperation("分页查询所有角色信息" )
    @GetMapping("/page" )
    public Result<Menu> findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam String name) {
        IPage<Menu> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if (!"".equals(name)) {
            queryWrapper.like("name" , name);
        }
        User currentUser = JwtUtil.getCurrentUser();
        System.out.println("当前用户------" + currentUser);
        return Result.success(menuService.page(page, queryWrapper));
    }

    @ApiOperation("刷新权限菜单" )
    @PostMapping("/refresh" )
    public Result<Menu> refresh(@RequestBody User user) {
        System.out.println(user.getRole());
        return Result.success(menuUtil.getRoleMenus(user.getRole()));
    }

}
