package com.canghuang.logincenter.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author cs
 * @date 2018/9/11
 * @description
 */
@Data
@Accessors(chain = true)
@TableName("s_user")
public class User  {

    private Long id;
    private String name;
    private String userface;
    private String account;
    private String password;
    private String salt;
    private LocalDateTime lastLoginTime;
    private String lastLoginIp;
    private LocalDateTime registerTime;

}
