package com.canghuang.logincenter.utils;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author cs
 * @date 2018/9/29
 * @description
 */
@Component
public class RedisKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object o, Method method, Object... objects) {


        return null;
    }
}
