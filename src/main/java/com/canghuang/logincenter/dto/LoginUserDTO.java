package com.canghuang.logincenter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author cs
 * @date 2019/2/12
 * @description
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "登录用户传输实体")
public class LoginUserDTO {

    @ApiModelProperty(value = "账号", example = "canghuang", readOnly = true)
    @NotEmpty(message = "账号不能为空")
    @Size(min = 8, max = 16, message = "账号长度应在{min}-{max}位之间")
    private String account;

    @ApiModelProperty(value = "密码", example = "123456")
    @NotEmpty(message = "密码不能为空")
    @Size(min = 6, max = 12, message = "密码长度应在{min}-{max}位之间")
    private String password;

}
