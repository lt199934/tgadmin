package com.tg.admin.mapper;

import com.tg.admin.dto.ArticleDto;
import com.tg.admin.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liutao
 * @since 2023-03-20
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    List<ArticleDto> findPage(Integer pageNum, Integer pageSize, String name);
    Integer userCount(String name);

}
