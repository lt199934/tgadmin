package com.tg.admin.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tg.admin.common.Result;
import com.tg.admin.entity.User;
import com.tg.admin.service.ArticleService;
import com.tg.admin.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Program: admin
 * @ClassName EchartsController
 * @Author: liutao
 * @Description: echarts图表
 * @Create: 2023-03-19 18:52
 * @Version 1.0
 **/
@Api(tags = "Echarts接口")
@RestController
@RequestMapping("/echarts")
public class EchartsController {
    @Autowired
    private UserService userService;
    @Resource
    private ArticleService articleService;
    @GetMapping
    public Result<Map<String,Object>> get() {
        Map<String, Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList(150, 230, 224, 218, 135, 147, 260));
        map.put("y", CollUtil.newArrayList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"));
        return Result.success(map);
    }

    @GetMapping("/mebers")
    public Result<Map<String,Object>> getMebers() {
        List<User> list = userService.list();
        int q1 = 0;
        int q2 = 0;
        int q3 = 0;
        int q4 = 0;
        for (User u:list) {
            Quarter quarter = DateUtil.quarterEnum(u.getCreateTime());
            switch (quarter) {
                case Q1: q1 +=1;break;
                case Q2: q2 +=1;break;
                case Q3: q3 +=1;break;
                case Q4: q4 +=1;break;
                default:break;
            }
        }
        HashMap<String,Object> map = new HashMap<>();
        ArrayList<Integer> arrayList = CollUtil.newArrayList(q1, q2, q3, q4);
        map.put("records",arrayList);
//        获取用户总数
        map.put("total",userService.list().size());
//        文章总数
        map.put("articleTotal",articleService.list().size());
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("online_status",true);
//        用户在线人数
        map.put("onlineTotal",userService.list(wrapper).size());
        return Result.success(map);
    }
}
