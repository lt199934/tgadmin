package com.tg.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tg.admin.entity.Files;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Program: admin
 * @ClassName FileMapper
 * @Author: liutao
 * @Description: file crud接口
 * @Create: 2023-03-16 18:56
 * @Version 1.0
 **/

@Mapper
public interface FileMapper extends BaseMapper<Files> {
}
