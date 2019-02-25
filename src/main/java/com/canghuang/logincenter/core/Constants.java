package com.canghuang.logincenter.core;

public enum Constants {

  // 获取必应小故事的api
  BING_STORY_API_HOST("https://api.berryapi.net/get/bing?AppKey=KIpvPXdids&id=%s");

  private String value;

  Constants(final String value) {
    this.value = value;
  }

  public String value() {
    return this.value;
  }
}
