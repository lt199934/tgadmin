package com.tg.admin.common.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.tg.admin.common.Constants;
import com.tg.admin.common.exception.ServiceException;
import com.tg.admin.config.Auth;
import com.tg.admin.entity.User;
import com.tg.admin.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Program: admin
 * @ClassName JwtInterceptor
 * @Author: liutao
 * @Description: 拦截器
 * @Create: 2023-03-14 06:04
 * @Version 1.0
 **/

public class JwtInterceptor implements HandlerInterceptor {
    private static final transient Logger log = LoggerFactory.getLogger(JwtInterceptor.class);
    @Autowired
    private UserServiceImpl userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        if(!(handler instanceof HandlerMethod)){
//            HandlerMethod h = (HandlerMethod) handler;
//            Auth auth = h.getMethodAnnotation(Auth.class);
//            if (auth == null) {
//                throw new ServiceException(Constants.CODE_401,"权限不足！");
//            }
//            auth = h.getBeanType().getAnnotation(Auth.class);
//            if (auth == null) {
//                throw new ServiceException(Constants.CODE_401,"权限不足！");
//            }
//            if (token != null) {
//                return true;
//            }
            return false;
        }
        log.info("token：{} ", token);
        // 认证
        if(StrUtil.isBlank(token)) {
            throw new ServiceException(Constants.CODE_401,"无token，请重新登录");
        }
        String id;
        try {
            id = JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException jwtDecodeException) {
            throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录");
        }
        User user = userService.getById(id);
        if(user == null){
            throw new ServiceException(Constants.CODE_401,"用户不存在，请重新登录");
        }
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        }catch (JWTVerificationException jwtVerificationException) {
            throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录");
        }
        return true;
    }

}
