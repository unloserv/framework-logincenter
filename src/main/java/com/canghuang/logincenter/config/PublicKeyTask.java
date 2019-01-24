package com.canghuang.logincenter.config;

import com.canghuang.logincenter.utils.PublicKeyGenerator;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author cs
 * @date 2019/1/23
 * @description
 */
@Component
@Configurable
@EnableScheduling
public class PublicKeyTask {

    @Scheduled(cron = "0 0/5 * * * ? ")
    void update(){
        PublicKeyGenerator.updatePublicKey();
    }
}
