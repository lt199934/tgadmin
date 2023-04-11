package com.tg.admin.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tg.admin.common.Result;
import com.tg.admin.config.Auth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tg.admin.service.ArticleCategoryService;
import com.tg.admin.entity.ArticleCategory;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liutao
 * @since 2023-03-26
 */
@Auth
@RestController
@Api(tags = "文章关联分类接口")
@RequestMapping("/article-category")
public class ArticleCategoryController {

@Resource
private ArticleCategoryService articleCategoryService;

/**
 * 查询所有接口
 * @return
 */
@ApiOperation(value = "查询所有接口", notes = "查询所有接口")
@GetMapping
public Result findAll() {
        return Result.success(articleCategoryService.list());
        }

/**
 * 根据id查询数据接口
 * @param id
 * @return
 */
@ApiOperation(value = "查更具id查询", notes = "根据id查询数据接口")
@GetMapping("/{id}")
public Result findOne(@PathVariable Integer id) {
        return Result.success(articleCategoryService.getById(id));
        }

/**
 * 分页查询接口
 * @param pageNum
 * @param pageSize
 * @return
 */
@ApiOperation(value = "分页查询接口", notes = "分页查询接口")
@GetMapping("/page")
public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize) {
        QueryWrapper<ArticleCategory> queryWrapper = new QueryWrapper<>();
        return Result.success(articleCategoryService.page(new Page<>(pageNum, pageSize), queryWrapper));
        }

/**
 * 新增和更新接口
 * @param
 * @return
 */
@ApiOperation(value = "新增和更新接口", notes = "新增和更新接口")
@PostMapping
public Result save(@RequestBody ArticleCategory articleCategory) {
    articleCategoryService.saveOrUpdate(articleCategory);
        return Result.success();
        }

/**
 * 删除接口
 * @param id
 * @return
 */
@ApiOperation(value = "删除接口", notes = "删除接口")
@DeleteMapping("/{id}")
public Result delete(@PathVariable Integer id) {
    articleCategoryService.removeById(id);
        return Result.success();
        }

/**
 * 批量删除接口
 * @param ids
 * @return
 */
@ApiOperation(value = "批量删除接口", notes = "批量删除接口")
@PostMapping("/del/batch")
public Result deleteBatch(@RequestBody List<Integer> ids) {
    articleCategoryService.removeByIds(ids);
        return Result.success();
        }
        }