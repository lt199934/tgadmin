package com.tg.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Program: admin
 * @ClassName File
 * @Author: liutao
 * @Description: 文件
 * @Create: 2023-03-16 18:51
 * @Version 1.0
 **/

@Data
@TableName("sys_file")
public class Files implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String type;
    private Long size;
    private String path;
    private String url;
    private Boolean isDelete;
    private Boolean enable;
    private String md5;
    private Timestamp uploadTime;
}
