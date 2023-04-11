package com.tg.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Program: admin
 * @ClassName Dict
 * @Author: liutao
 * @Description:
 * @Create: 2023-03-20 01:09
 * @Version 1.0
 **/

@Data
@TableName("sys_dict")
public class Dict {
    @TableId(type=IdType.AUTO)
    private Integer id;
    private String name;
    private  String value;
    private  String type;
}
