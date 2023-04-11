package com.tg.admin.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.tg.admin.utils.RedisUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * 验证码操作处理
 *
 * @author liutao
 */
@Api(tags = "验证码接口")
@RequestMapping("/captcha")
@RestController
public class CaptchaController {
    private static final transient Logger log = LoggerFactory.getLogger(CaptchaController.class);
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/code")
    public void getCode(HttpSession session, HttpServletResponse response) throws IOException {
        // 利用 hutool 工具，生成验证码图片资源
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 5);

        // 获得生成的验证码字符
        String code = captcha.getCode();
        // 判断如果重新生成就延期
        if (redisTemplate.hasKey("code")) {
            redisTemplate.expire("code", 60, TimeUnit.SECONDS);
            log.info("code：{}", "续期成功");
        }
        //将凭证存入Redis,设置过期时间，1分钟
        redisUtil.set("code", code, 60L, TimeUnit.SECONDS);
        log.info("生成code：{}", code);
        // 将验证码图片的二进制数据写入【响应体 response 】
        captcha.write(response.getOutputStream());
    }

}
