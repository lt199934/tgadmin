package com.tg.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tg.admin.common.Constants;
import com.tg.admin.common.Result;
import com.tg.admin.entity.Dict;
import com.tg.admin.entity.Role;
import com.tg.admin.entity.User;
import com.tg.admin.service.DictService;
import com.tg.admin.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Program: admin
 * @ClassName DictController
 * @Author: liutao
 * @Description:
 * @Create: 2023-03-20 01:13
 * @Version 1.0
 **/
@Api(tags = "字典接口")
@RestController
@RequestMapping("/dict")
public class DictController {
    @Autowired
    private DictService dictService;
    @GetMapping("/icons")
    public Result<Dict> getIcons(){
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.eq("type", Constants.DICT_TYPE_ICON);
        return Result.success(dictService.list(dictQueryWrapper));
    }

    @ApiOperation("分页查询所有角色信息")
    @GetMapping("/page")
    public Result<Dict> finaPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam String name) {
        IPage<Dict> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)) {
            queryWrapper.like("name", name);
        }
        User currentUser = JwtUtil.getCurrentUser();
        System.out.println("当前用户------" + currentUser);
        return Result.success(dictService.page(page, queryWrapper));
    }
    @ApiOperation("更新或新增")
    @PostMapping
    public Result<Dict> save(@RequestBody Dict dict){
        return Result.success(dictService.saveOrUpdate(dict));
    }

    @ApiOperation("根据id删除字典")
    @DeleteMapping("/{id}")
    public Result<Dict> delete(@PathVariable Integer id) {
        return Result.success(dictService.removeById(id));
    }


    @ApiOperation("根据id批量删除字典")
    @PostMapping("del/batch")
    public Result<Dict> deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(dictService.removeBatchByIds(ids));
    }
}
