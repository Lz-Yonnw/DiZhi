package com.ruoyi.system.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "用户唯一登录")
public class TbUserSole {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String phoneNumber;
}
