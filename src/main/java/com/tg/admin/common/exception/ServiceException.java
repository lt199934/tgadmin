package com.tg.admin.common.exception;

import lombok.Getter;

/**
 * @Program: admin
 * @ClassName ServiceException
 * @Author: liutao
 * @Description:
 * @Create: 2023-03-14 03:52
 * @Version 1.0
 **/

@Getter
public class ServiceException extends RuntimeException{
    private String code;
    public ServiceException(String code,String msg){
        super(msg);
        this.code = code;
    }

}
