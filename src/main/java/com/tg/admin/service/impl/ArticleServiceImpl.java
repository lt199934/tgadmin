package com.tg.admin.service.impl;

import com.tg.admin.dto.ArticleDto;
import com.tg.admin.entity.Article;
import com.tg.admin.mapper.ArticleMapper;
import com.tg.admin.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liutao
 * @since 2023-03-20
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public List<ArticleDto> findPage(Integer pageNum, Integer pageSize, String username) {
        return null;
    }

    @Override
    public Integer userCount(String username) {
        return null;
    }
}
