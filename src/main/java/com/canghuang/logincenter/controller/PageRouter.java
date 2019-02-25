package com.canghuang.logincenter.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.canghuang.logincenter.core.BingStory.BingStoryUtils;
import com.canghuang.logincenter.utils.AccessToken;
import com.canghuang.logincenter.utils.PublicKeyGenerator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

/**
 * @author cs
 * @date 2019/1/21
 * @description
 */
@Controller
public class PageRouter {

  @Autowired private StringRedisTemplate stringRedisTemplate;

  @Autowired private BingStoryUtils bingStoryUtils;

  @GetMapping("/verify")
  public Mono<String> verify(
      @RequestParam("redirectUrl") String redirectUrl,
      @RequestParam(value = "accessToken", required = false) String accessToken) {
    // 没有accessToken跳转到登录页
    if (StringUtils.isEmpty(accessToken)) {
      return Mono.just(buildLoginPage(redirectUrl));
    }
    // 验证登录凭证是否有效
    DecodedJWT decodedJWT;
    try {
      decodedJWT = AccessToken.verify(accessToken);
    } catch (JWTVerificationException e) {
      decodedJWT = null;
    }
    // 无效accessToken，跳转到登录页
    if (decodedJWT == null || decodedJWT.getKeyId() == null) {
      return Mono.just(buildLoginPage(redirectUrl));
    }
    // 验证accessToken是否存或已过期
    boolean loginFlag = stringRedisTemplate.hasKey(decodedJWT.getKeyId());
    if (loginFlag) {
      String token = stringRedisTemplate.opsForValue().get(decodedJWT.getKeyId());
      return Mono.just("redirect:" + redirectUrl + "?accessToken=" + token);
    } else {
      return Mono.just(buildLoginPage(redirectUrl));
    }
  }

  private String buildLoginPage(String redirectUrl) {
    return "redirect:loginPage"
        + "?"
        + "redirectUrl="
        + redirectUrl
        + "&"
        + "authorization="
        + PublicKeyGenerator.publicKey;
  }

  @GetMapping("/loginPage")
  public String loginPage(
      final Model model,
      @RequestParam("redirectUrl") String redirectUrl,
      @RequestParam("authorization") String authorization) {
    model.addAttribute("authorization", authorization);
    model.addAttribute("bingStoryQueue", bingStoryUtils.getStoryQueue());
    model.addAttribute("redirectUrl", redirectUrl);
    return "loginPage.html";
  }
}
