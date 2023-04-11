package com.tg.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.admin.entity.Files;
import com.tg.admin.mapper.FileMapper;
import com.tg.admin.service.FileService;
import org.springframework.stereotype.Service;

/**
 * @Program: admin
 * @ClassName FileServiceImpl
 * @Author: liutao
 * @Description:
 * @Create: 2023-03-16 18:58
 * @Version 1.0
 **/

@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, Files> implements FileService {

    @Override
    public Files getOne(Wrapper<Files> queryWrapper) {
        return super.getOne(queryWrapper);
    }
}
