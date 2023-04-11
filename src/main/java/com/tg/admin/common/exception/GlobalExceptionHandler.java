package com.tg.admin.common.exception;

import com.tg.admin.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Program: admin
 * @ClassName ExceptionHandler
 * @Author: liutao
 * @Description: 自定义异常
 * @Create: 2023-03-14 03:52
 * @Version 1.0
 **/

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * @MethodName: handle
     * @description: 业务异常
     * @Author: LiuTao
     * @Param: [se]
     * @UpdateTime: 2023/3/14 4:00
     * @Return: com.tg.admin.common.Result
     * @Throw:
     **/

    @ExceptionHandler
    @ResponseBody
    public Result handle(ServiceException se){
        return Result.error(se.getCode(),se.getMessage());
    }

}
