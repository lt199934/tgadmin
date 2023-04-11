package com.tg.admin.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tg.admin.common.Result;
import com.tg.admin.config.Auth;
import com.tg.admin.entity.Category;
import com.tg.admin.service.CategoryService;
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
 * @since 2023-03-25
 */
@Auth
@RestController
@Api(tags = "分类接口" )
@RequestMapping("/category" )
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 查询所有接口
     *
     * @return
     */
    @ApiOperation(value = "查询所有接口" , notes = "查询所有接口" )
    @GetMapping
    public Result<Category> findAll() {
        return Result.success(categoryService.list());
    }

    /**
     * 根据id查询数据接口
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "查更具id查询" , notes = "根据id查询数据接口" )
    @GetMapping("/{id}" )
    public Result<Category> findOne(@PathVariable Integer id) {
        return Result.success(categoryService.getById(id));
    }

    /**
     * 分页查询接口
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "分页查询接口" , notes = "分页查询接口" )
    @GetMapping("/page" )
    public Result<Category> findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam String name) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)) {
            queryWrapper.eq("name" , name);
        }
        return Result.success(categoryService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    /**
     * 新增和更新接口
     *
     * @param
     * @return
     */
    @ApiOperation(value = "新增和更新接口" , notes = "新增和更新接口" )
    @PostMapping
    public Result<Category> save(@RequestBody Category category) {
        categoryService.saveOrUpdate(category);
        return Result.success();
    }

    /**
     * 删除接口
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除接口" , notes = "删除接口" )
    @DeleteMapping("/{id}" )
    public Result<Category> delete(@PathVariable Integer id) {
        categoryService.removeById(id);
        return Result.success();
    }

    /**
     * 批量删除接口
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除接口" , notes = "批量删除接口" )
    @PostMapping("/del/batch" )
    public Result<Category> deleteBatch(@RequestBody List<Integer> ids) {
        categoryService.removeByIds(ids);
        return Result.success();
    }
}