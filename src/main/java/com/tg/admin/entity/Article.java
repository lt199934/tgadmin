package com.tg.admin.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2023-03-20
 */
@Getter
@Setter
@ApiModel(value = "Article对象", description = "")
@TableName("article")
public class Article implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("文章id")
    @TableId(type = IdType.AUTO)
      private Integer id;

    @ApiModelProperty("title")
    private String title;

    @ApiModelProperty("文章内容")
    private String content;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("发表时间")
    private Timestamp createTime;


}
