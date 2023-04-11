package com.tg.admin.utils;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import javax.servlet.http.HttpServletRequest;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.tg.admin.entity.User;
import com.tg.admin.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Objects;

/**
 * @Program: admin
 * @ClassName JWTUtil
 * @Author: liutao
 * @Description: jwt
 * @Create: 2023-03-14 05:46
 * @Version 1.0
 **/
@Component
public class JwtUtil {
    private static UserServiceImpl staticUserVice;

    @Autowired
    private UserServiceImpl userService;

    @PostConstruct
    public void setStaticUserVice() {
        staticUserVice = userService;
    }

    public static String getToken(String id, String sign) {
        return JWT.create().withAudience(id)
                .withExpiresAt(DateUtil.offsetHour(new Date(), 1)) //2小时过期
                .sign(Algorithm.HMAC256(sign)); //密钥
    }

    public static User getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (StrUtil.isNotBlank(token)) {
                String id = JWT.decode(token).getAudience().get(0);
                return staticUserVice.getById(Integer.valueOf(id));
            }
        } catch (Exception ex) {
            return null;
        }
        return null;
    }

}
