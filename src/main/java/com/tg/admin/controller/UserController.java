package com.tg.admin.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tg.admin.common.Constants;
import com.tg.admin.common.Result;
import com.tg.admin.dto.PwdDto;
import com.tg.admin.dto.UserDto;
import com.tg.admin.entity.User;
import com.tg.admin.service.UserService;
import com.tg.admin.utils.JwtUtil;
import com.tg.admin.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Program: admin
 * @ClassName UserController
 * @Author: liutao
 * @Description: 用户web接口
 * @Create: 2023-03-12 07:11
 * @Version 1.0
 **/

@Api(tags = "用户接口", basePath = "/user")
@RestController
@RequestMapping("/user")
public class UserController {
    private static final transient Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "登录接口", httpMethod = "POST")
    @PostMapping("/login")
    public Result<User> login(@RequestBody UserDto userDto) {
        log.info("当前验证码：{}----------->服务器验证码：{}", userDto.getCode(), redisUtil.get("code"));
        if (!redisUtil.exists("code")) {
            return Result.error(Constants.CODE_400, "验证码失效，请重新获取");
        }
        if (!userDto.getCode().equals(redisUtil.get("code"))) {
            return Result.error(Constants.CODE_400, "验证码错误");
        }
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        UserDto user = userService.login(userDto);
        // 登录成功清除验证码
        redisUtil.remove("code");
        return Result.success(user);
    }

    @ApiOperation(value = "注册接口", httpMethod = "POST")
    @PostMapping("/register")
    public Result<User> regis(@RequestBody UserDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        return Result.success(userService.register(userDto));
    }

    @ApiOperation("修改密码")
    @PostMapping("/upPwd")
    public Result<User> upPwd(@RequestBody PwdDto pwdDto) {
        User currentUser = JwtUtil.getCurrentUser();
        User user = null;
        QueryWrapper<User> queryWrapper = null;
        if (currentUser != null) {
            user = userService.getById(currentUser.getId());
        }
        if (user != null) {
            if (!pwdDto.getOldPwd().equals(user.getPassword())) {
                return Result.error(Constants.CODE_400, "旧密码错误");
            } else {
                user.setPassword(pwdDto.getNewPwd());
                queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id", user.getId());
                queryWrapper.eq("password", pwdDto.getOldPwd());
            }
        }
        return Result.success(userService.update(user, queryWrapper));
    }

    @ApiOperation("退出登录")
    @GetMapping("logout")
    public Result<User> logout() {
        User currentUser = JwtUtil.getCurrentUser();
        currentUser.setOnlineStatus(false);
        return Result.success(userService.updateById(currentUser));
    }

    @ApiOperation("根据id查询用户信息")
    @GetMapping("/person")
    public Result<User> findOne() {
        User currentUser = JwtUtil.getCurrentUser();
        return Result.success(userService.getById(currentUser.getId()));
    }

    /**
     * @MethodName: save
     * @description: mybatisplus接口
     * @Author: LiuTao
     * @Param: [user]
     * @UpdateTime: 2023/3/12 14:19
     * @Return: java.lang.Boolean
     * @Throw:
     **/
    @ApiOperation(value = "更新或新增", httpMethod = "POST")
    @PostMapping
    public Result<User> save(@RequestBody User user) {
//        自己写的
//        return  userService.saveUser(user);
//        mybatisplus提供的
        return Result.success(userService.saveOrUpdate(user));
    }

    @ApiOperation("查询所有")
    @GetMapping
    public Result<User> findAll() {
        List<User> list = userService.findAll();
        System.out.println(list);
        return Result.success(list);
    }

    @ApiOperation("根据id删除用户")
    @DeleteMapping("/{id}/{status}")
    public Result<User> delete(@PathVariable Integer id, @PathVariable Integer status) {
        User user = userService.getById(id);
        user.setId(id);
        user.setStatus(2);
        return Result.success(userService.updateById(user));
    }

    @ApiOperation("不使用框架")
    @GetMapping("/page1")
    public Map<String, Object> findPage1(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam String username, @RequestParam String email, @RequestParam String address) {
        pageNum = (pageNum - 1) * pageSize;
        Map<String, Object> resp = new HashMap<String, Object>();
        resp.put("total", userService.userCount(username, email, address));
        resp.put("data", userService.findPage(pageNum, pageSize, username, email, address));
        return resp;
    }

    @ApiOperation("分页查询所有用户信息")
    @GetMapping("/page")
    public Result<User> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam String username, @RequestParam String email, @RequestParam String address) {
        IPage<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!"".equals(username)) {
            queryWrapper.like("username", username);
        }
        if (!"".equals(email)) {
            queryWrapper.like("email", email);
        }
        if (!"".equals(address)) {
            queryWrapper.like("address", address);
        }
        User currentUser = JwtUtil.getCurrentUser();
        System.out.println("当前用户------" + currentUser);
        return Result.success(userService.page(page, queryWrapper));
    }

    /***
     * @MethodName: deleteBatch
     * @description: 批量删除
     * @Author: LiuTao
     * @Param: [data]
     * @UpdateTime: 2023/3/13 14:47
     * @Return: java.lang.Boolean
     * @Throw:
     **/
    @ApiOperation("批量删除用户")
    @PostMapping("/batch")
    public Result<User> deleteBatch(@RequestBody List<User> data) {
        System.out.println(data);
        return Result.success(userService.updateBatchById(data));
    }

    @ApiOperation("导出")
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
//        从数据库查询所有数据
        List<User> users = userService.list();
//        通过工具类创建writer 写出到磁盘
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "用户信息.xlsx");
//        在内存操作 ，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
//        自定义标题名
        writer.addHeaderAlias("id", "用户id");
        writer.addHeaderAlias("username", "用户名");
        writer.addHeaderAlias("password", "密码");
        writer.addHeaderAlias("nickname", "昵称");
        writer.addHeaderAlias("avatarUrl", "头像");
        writer.addHeaderAlias("sex", "性别");
        writer.addHeaderAlias("age", "年龄");
        writer.addHeaderAlias("email", "邮箱");
        writer.addHeaderAlias("phone", "电话");
        writer.addHeaderAlias("address", "地址");
        writer.addHeaderAlias("onlineStatus", "在线状态");
        writer.addHeaderAlias("status", "用户状态");
        writer.addHeaderAlias("createTime", "注册时间");
//        一次性写出users内的对象到excel，使用默认样式，强制输出标题
        writer.write(users, true);
//        设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
//        获取输出流
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }

    @ApiOperation("导出")
    @PostMapping("/import")
    public Result<User> imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
//        List<User> list = reader.readAll(User.class);
        List<List<Object>> list = reader.read(1);
        List<User> users = CollUtil.newArrayList();
        for (List<Object> row : list) {
            User user = new User();
            user.setUsername(row.get(1).toString());
            user.setPassword(row.get(2).toString());
            user.setNickname(row.get(3).toString());
            user.setAvatarUrl(row.get(4).toString());
            user.setSex(Integer.parseInt(row.get(5).toString()));
            user.setAge(Integer.parseInt(row.get(6).toString()));
            user.setEmail(row.get(7).toString());
            user.setPhone(row.get(8).toString());
            user.setAddress(row.get(9).toString());
            user.setOnlineStatus(Integer.parseInt(row.get(10).toString()) == 0 ? false : true);
            user.setStatus(Integer.parseInt(row.get(11).toString()));
            users.add(user);
        }
        return Result.success(userService.saveBatch(users));

    }
}
