package com.tg.admin.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Program: admin
 * @ClassName PwdDto
 * @Author: liutao
 * @Description:
 * @Create: 2023-03-20 09:39
 * @Version 1.0
 **/
@ApiModel("密码视图，用于前端请求")
@Data
public class PwdDto {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String oldPwd;
    private String newPwd;
}
