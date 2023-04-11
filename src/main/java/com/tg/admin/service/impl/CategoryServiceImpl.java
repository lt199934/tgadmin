package com.tg.admin.service.impl;

import com.tg.admin.entity.Category;
import com.tg.admin.mapper.CategoryMapper;
import com.tg.admin.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liutao
 * @since 2023-03-25
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
