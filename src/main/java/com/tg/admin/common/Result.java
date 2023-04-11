package com.tg.admin.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Program: admin
 * @ClassName Result
 * @Author: liutao
 * @Description: 通用结果
 * @Create: 2023-03-14 03:37
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private String code;
    private String msg;
    private Object data;

    public static<T> Result<T> success() {
        return new Result<T>(Constants.CODE_200, "", null);
    }

    public static<T> Result<T> success(Object data) {
        return new Result<T>(Constants.CODE_200, "成功", data);
    }

    public static<T> Result<T> waring(String msg, Object data) {
        return new Result<T>(Constants.CODE_550, msg, data);
    }

    public static<T> Result<T> error(String code, String msg) {
        return new Result<T>(code, msg, null);
    }

    public static<T> Result<T> error() {
        return new Result<T>(Constants.CODE_500, "系统错误", null);
    }
}
