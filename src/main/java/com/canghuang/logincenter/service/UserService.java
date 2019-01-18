package com.canghuang.logincenter.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.canghuang.logincenter.entity.User;
import com.canghuang.logincenter.mapper.UserMapper;
import com.canghuang.logincenter.utils.EncryptUtil;
import com.canghuang.logincenter.utils.RedisKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * @author cs
 * @date 2018/9/17
 * @description
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public Page<User> getUserPage() {
        return null;
    }

    public boolean saveUser(User user) {
        String salt = EncryptUtil.createSalt();
        user.setSalt(salt);
        user.setPassword(EncryptUtil.encrypt(user.getPassword(), salt));
        user.setRegisterTime(LocalDateTime.now());
        int result = userMapper.insert(user);
        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    public User validateUserExisit(String account) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("account", account));
    }

    public int validateUser (User user, String password){
        String pass = EncryptUtil.encrypt(password, user.getSalt());
        if (user.getPassword().equals(pass)) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * 使用缓存
     * @param username
     * @return
     */
    @Cacheable(value = "userCache" , keyGenerator = "redisKeyGenerator")
    public User getUserByUsername(String username) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }

}
