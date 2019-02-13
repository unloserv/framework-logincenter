package com.canghuang.logincenter.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.canghuang.logincenter.dto.LoginUserDTO;
import com.canghuang.logincenter.entity.User;
import com.canghuang.logincenter.service.UserService;
import com.canghuang.logincenter.utils.AccessToken;
import com.canghuang.logincenter.utils.EncryptUtil;
import com.canghuang.logincenter.utils.PublicKeyGenerator;
import com.canghuang.logincenter.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @author cs
 * @date 2018/9/8
 * @description
 */
@Slf4j
@Api(value = "登录接口")
@RestController
public class HomeController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Mono<Result> register(@RequestBody User user) {
        if (StringUtils.isEmpty(user.getName()) || user.getName().length() < 2 || user.getName().length() > 10) {
            return Mono.just(Result.failure("名称不符合要求，请检查"));
        }
        try {
            boolean flag = userService.saveUser(user);
            if (flag) {
                return Mono.just(Result.success("用户注册成功"));
            } else {
                return Mono.just(Result.failure("用户注册失败"));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return Mono.just(Result.error());
        }
    }

    /**
     *              登录请求
     *                 |
     *            判断是否已登录
     *                 |
     *          未登录 --- 已登录
     *            |         |
     *       验证用户名密码  返回token
     *            |
     *       正确 --- 错误
     *        |       |
     *     存入缓存  返回登录失败
     *   并返回token
     */
    @PostMapping("/login")
    public Mono<Result> login(@RequestHeader("Authorization") String authorization,
                              @ApiParam(required = true) @Validated final LoginUserDTO loginUserDTO) {
        if (StringUtils.isEmpty(authorization) || !PublicKeyGenerator.publicKey.equals(authorization)) {
            return Mono.just(Result.failure("非法访问"));
        }
        final String account = loginUserDTO.getAccount();
        final String password = loginUserDTO.getPassword();
        // 根据用户名生成tokenId
        final String tokenId = EncryptUtil.md5Encode(account);
        // 判断是否已登录
        final boolean loginFlag = stringRedisTemplate.hasKey(tokenId);
        final String token;
        if (loginFlag) {
            // 已登录 从redis中获取token
            token = stringRedisTemplate.opsForValue().get(tokenId);
            return Mono.just(Result.success(null, token));
        } else {
            // 未登录 验证用户是否存在
            final User user = userService.validateUserExisit(account);
            if (user == null) {
                return Mono.just(Result.failure("用户不存在，请检查用户名是否正确"));
            }
            // 验证用户名密码是否匹配
            final int flag = userService.validateUser(user, password);
            switch (flag) {
                case 1:
                // 验证成功
                    // 计算过期时间
                    final Date expertTime = new Date(LocalDateTime.now().plusDays(1L).toInstant(ZoneOffset.of("+8")).toEpochMilli());
                    // 生成令牌
                    token = AccessToken.build(tokenId, expertTime, user);
                    // 将令牌存入redis
                    stringRedisTemplate.opsForValue().set(tokenId, token);
                    // 设定过期时间
                    stringRedisTemplate.expireAt(tokenId, expertTime);
                    return Mono.just(Result.success(null, token));
                case 2:
                // 验证失败
                    return Mono.just(Result.failure("身份验证失败，请检查密码是否正确"));
                default:
                    return Mono.just(Result.error());
            }
        }
    }

    @PutMapping("/logout")
    public Mono<Result> logout(@RequestParam("accessToken") String accessToken) {
        try {
            // 1.验证token是否合法
            if (StringUtils.isEmpty(accessToken)) {
                return Mono.just(Result.failure("token不符合要求，请检查"));
            }
            String tokenId = AccessToken.verify(accessToken).getKeyId();
            if (tokenId == null) {
                return Mono.just(Result.failure("token验证失败: " + accessToken));
            }
            // 2.验证用户是否已登录
            boolean loginFlag = stringRedisTemplate.hasKey(tokenId);
            if (loginFlag) {
                String token = stringRedisTemplate.opsForValue().get(tokenId);
                if (!token.equals(accessToken)) {
                    return Mono.just(Result.failure("token验证失败: " + accessToken));
                }
                // 3.退出登录
                stringRedisTemplate.delete(tokenId);
                return Mono.just(Result.success("再见"));
            } else {
                return Mono.just(Result.success("没登录瞎退出什么"));
            }
        } catch (JWTVerificationException e) {
            log.warn(e.toString());
        }
        return Mono.just(Result.failure("发生错误"));
    }

}
