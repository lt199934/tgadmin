package com.tg.admin.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tg.admin.common.Result;
import com.tg.admin.entity.Files;
import com.tg.admin.entity.User;
import com.tg.admin.service.FileService;
import com.tg.admin.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Program: admin
 * @ClassName: FileController
 * @Author: liutao
 * @Description: 文件处理
 * @Create: 2023-03-16 18:15
 * @Version 1.0
 **/
@Api(tags = "文件接口")
@RestController
@RequestMapping("/file")
public class FileController {
    @Value("${files.upload.path}")
    private String fileUploadPath;
    @Value("${server.port}")
    private String port;
    @Value("${files.ip}")
    private String ip;
    @Autowired
    private FileService fileService;

    @ApiOperation("分页查询所有文件信息")
    @GetMapping("/page")
    public Result<Files> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam String name, @RequestParam String type) {
        IPage<Files> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_Delete", false);
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        if (!"".equals(type)) {
            queryWrapper.like("type", type);
        }
        User currentUser = JwtUtil.getCurrentUser();
        System.out.println("当前用户------" + currentUser);
        return Result.success(fileService.page(page, queryWrapper));
    }

    /**
     * @MethodName: upload
     * @description: 文件上传
     * @Author: LiuTao
     * @Param: [file]
     * @UpdateTime: 2023/3/16 18:39
     * @Return: java.lang.String
     * @Throw: IOException
     **/
    @PostMapping("/upload")
    public Result<Files> upload(@RequestParam MultipartFile file, HttpServletRequest request) throws IOException {
//        文件原始名
        String originalFilename = file.getOriginalFilename();
//        文件类型
        String type = FileUtil.extName(originalFilename);
//        文件大小
        long size = file.getSize();
        String today = DateUtil.today();
        //        定义一个文件唯一的标识码
        String fileUUID = IdUtil.fastSimpleUUID() + StrUtil.DOT + type;
        //  判断目录是否存在。不存在就创建
        if (!FileUtil.exist(fileUploadPath+today)) {
            FileUtil.mkdir(fileUploadPath+today);
        }
        //上传的文件
        File uploadFile = new File(fileUploadPath + StrUtil.C_SLASH + fileUUID);
        System.out.println(fileUploadPath);
        //      文件存入磁盘
        file.transferTo(uploadFile);
//      获取文件md5
        String md5 = SecureUtil.md5(uploadFile);
//      查询数据库有没有当前md5
        Files one = getFileMd5(md5);
        String url;
        if (one != null) {
            url = one.getUrl();
            uploadFile.delete();
            return Result.waring("文件重复", one.getUrl());
        } else {
            url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/file/" + fileUUID;
        }
//        存入数据库
        Files saveFile = new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size / 1024);
        saveFile.setPath(uploadFile.getCanonicalPath());
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        fileService.save(saveFile);
        return Result.success(url);
    }

    @ApiOperation("根据id删除文件")
    @DeleteMapping("/{id}")
    public Result<Files> delete(@PathVariable Integer id) {
        Files files = fileService.getById(id);
        files.setIsDelete(true);
        fileService.updateById(files);
        return Result.success();
    }


    @ApiOperation("根据id批量删除文件")
    @PostMapping("del/batch")
    public Result<Files> deleteBatch(@RequestBody List<Integer> ids) {
        QueryWrapper<Files> queryWrapper = new QueryWrapper<Files>();
        queryWrapper.in("id", ids);
        List<Files> files = fileService.list(queryWrapper);
        for (Files file : files) {
            file.setIsDelete(true);
            fileService.updateById(file);
        }
        return Result.success();
    }

    @ApiOperation(value = "更新或新增", httpMethod = "POST")
    @PostMapping("/update")
    public Result<Files> save(@RequestBody Files files) {
        return Result.success(fileService.saveOrUpdate(files));
    }

    @ApiOperation("下载文件接口")
    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        File uploadFile = new File(fileUploadPath + StrUtil.C_SLASH + fileUUID);
        ServletOutputStream outputStream = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename =" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");
        byte[] bytes = FileUtil.readBytes(uploadFile);
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    private Files getFileMd5(String md5) {
        QueryWrapper<Files> wrapper = new QueryWrapper<>();
        wrapper.eq("md5", md5);
        List<Files> list = fileService.list(wrapper);
        return list.size() == 0 ? null : list.get(0);
    }
}


