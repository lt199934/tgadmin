package com.tg.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tg.admin.common.Result;
import com.tg.admin.entity.Role;
import com.tg.admin.entity.User;
import com.tg.admin.service.RoleService;
import com.tg.admin.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author liutao
 * @since 2023-03-19
 */
@Api(tags = "角色接口")
@RestController
@RequestMapping("/role" )
public class RoleController {
    @Autowired
    private RoleService roleService;


    @ApiOperation(value = "更新或新增" , httpMethod = "POST" )
    @PostMapping
    public Result<Role> save(@RequestBody Role role) {
        return Result.success(roleService.saveOrUpdate(role));
    }

    @GetMapping
    public Result<Role> findAll() {
        return Result.success(roleService.list());
    }


    @ApiOperation("根据id删除角色" )
    @DeleteMapping("/{id}" )
    public Result<Role> delete(@PathVariable Integer id) {
        return Result.success(roleService.removeById(id));
    }


    @ApiOperation("根据id批量删除文件" )
    @PostMapping("/del/batch" )
    public Result<Role> deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(roleService.removeBatchByIds(ids));
    }

    @ApiOperation("分页查询所有角色信息" )
    @GetMapping("/page" )
    public Result<Role> finaPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam String name) {
        IPage<Role> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if (!"".equals(name)) {
            queryWrapper.like("name" , name);
        }
        User currentUser = JwtUtil.getCurrentUser();
        System.out.println("当前用户------" + currentUser);
        return Result.success(roleService.page(page, queryWrapper));
    }


    @ApiOperation("更新权限菜单" )
    @PostMapping("/roleMenu/{roleId}" )
    public Result<Role> roleMenu(@PathVariable Integer roleId, @RequestBody List<Integer> menuIds) {
        roleService.setRoleMenu(roleId, menuIds);
        return Result.success();
    }

    @ApiOperation("更新权限菜单" )
    @GetMapping("/roleMenu/{roleId}" )
    public Result<Role> getRoleMenu(@PathVariable Integer roleId) {
        return Result.success(roleService.setRoleMenu(roleId));
    }


}
