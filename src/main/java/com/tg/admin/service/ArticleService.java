package com.tg.admin.service;

import com.tg.admin.dto.ArticleDto;
import com.tg.admin.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tg.admin.entity.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liutao
 * @since 2023-03-20
 */
public interface ArticleService extends IService<Article> {
    List<ArticleDto> findPage(Integer pageNum, Integer pageSize, String name);
    Integer userCount(String username);

}
