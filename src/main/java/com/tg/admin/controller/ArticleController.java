package com.tg.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tg.admin.common.Result;
import com.tg.admin.entity.Article;
import com.tg.admin.entity.Dict;
import com.tg.admin.entity.User;
import com.tg.admin.service.ArticleService;
import com.tg.admin.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liutao
 * @since 2023-03-20
 */
@Api(tags = "文章接口")
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @ApiOperation("分页查询所有文章信息")
    @GetMapping("/page")
    public Result<Article> findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam String name) {
        IPage<Article> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)) {
            queryWrapper.like("title", name);
        }
        User currentUser = JwtUtil.getCurrentUser();
        System.out.println("当前用户------" + currentUser);
        return Result.success(articleService.page(page, queryWrapper));
    }

    @ApiOperation("不使用框架")
    @GetMapping("/page1")
    public Map<String, Object> findPage1(@RequestParam Integer pageNum,
                                         @RequestParam Integer pageSize,
                                         @RequestParam String title) {
        pageNum = (pageNum - 1) * pageSize;
        Map<String, Object> resp = new HashMap<String, Object>();
        resp.put("total", articleService.userCount(title));
        resp.put("data", articleService.findPage(pageNum, pageSize, title));
        return resp;
    }
    @ApiOperation("更新或新增")
    @PostMapping
    public Result<Article> save(@RequestBody Article article){
        if(article.getId() == null) {
            article.setAuthor(JwtUtil.getCurrentUser().getNickname());
        }
        return Result.success(articleService.saveOrUpdate(article));
    }

    @ApiOperation("根据id删除文章")
    @DeleteMapping("/{id}")
    public Result<Article> delete(@PathVariable Integer id) {
        return Result.success(articleService.removeById(id));
    }


    @ApiOperation("根据id批量删除文章")
    @PostMapping("/del/batch")
    public Result<Article> deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(articleService.removeBatchByIds(ids));
    }

}
