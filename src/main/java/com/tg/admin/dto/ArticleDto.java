package com.tg.admin.dto;

import com.tg.admin.entity.Article;
import com.tg.admin.entity.Category;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Program: admin
 * @ClassName ArtileDto
 * @Author: liutao
 * @Description: 文章分类
 * @Create: 2023-04-08 02:14
 * @Version 1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Article文章dto")
public class ArticleDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Article article;
    private List<Category> category;

}
