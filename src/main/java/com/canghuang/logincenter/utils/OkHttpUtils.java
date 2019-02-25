package com.canghuang.logincenter.utils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author cs
 * @date 2019/2/21
 * @description
 */
@Slf4j
@Component
public class OkHttpUtils {

  @Autowired OkHttpClient okHttpClient;

  /**
   * 根据map获取get请求参数
   *
   * @param queries
   * @return
   */
  public StringBuffer getQueryString(String url, Map<String, String> queries) {
    StringBuffer sb = new StringBuffer(url);
    if (queries != null && queries.keySet().size() > 0) {
      boolean firstFlag = true;
      Iterator iterator = queries.entrySet().iterator();
      while (iterator.hasNext()) {
        Map.Entry entry = (Map.Entry<String, String>) iterator.next();
        if (firstFlag) {
          sb.append("?" + entry.getKey() + "=" + entry.getValue());
          firstFlag = false;
        } else {
          sb.append("&" + entry.getKey() + "=" + entry.getValue());
        }
      }
    }
    return sb;
  }

  /**
   * 发送http GET请求
   *
   * @param api
   * @return
   */
  public String sendGetRequest(final String api) {
    Response response = null;
    try {
      Request request = new Request.Builder().url(api).build();
      response = okHttpClient.newCall(request).execute();
      log.debug("testokhttp成功，url:'{}'，result:'{}'", api, response.body().string());
      return response.body().string();
    } catch (IOException e) {
      log.error("testokhttp失败，url:'{}'", api);
      e.printStackTrace();
    } finally {
      if (response.body() != null) {
        response.body().close(); // 一定要关闭，不然会泄露资源
      }
    }
    return null;
  }
}
