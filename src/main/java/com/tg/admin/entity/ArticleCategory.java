package com.tg.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author liutao
 * @since 2023-03-26
 */
@Getter
@Setter
@TableName("article_category")
@ApiModel(value = "ArticleCategory对象", description = "")
public class ArticleCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分类id")
      private Integer categoryId;

    @ApiModelProperty("文章id")
      private Integer aiticleId;


}
