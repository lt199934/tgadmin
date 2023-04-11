package com.tg.admin.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.admin.entity.Dict;
import com.tg.admin.entity.Role;
import com.tg.admin.mapper.DictMapper;
import com.tg.admin.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liutao
 * @since 2023-03-19
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

}
