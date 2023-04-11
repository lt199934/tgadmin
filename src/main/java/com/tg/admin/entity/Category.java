package com.tg.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2023-03-25
 */
@Getter
@Setter
@ApiModel(value = "Category对象", description = "")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分类id")
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("父级id")
    private Integer pid;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("分类别名")
    private String alias;

    @ApiModelProperty("描述")
    private String description;


}
