package com.canghuang.logincenter.core;

public enum Constants {

  // 获取必应小故事的api
  //BING_STORY_API_HOST("https://api-cn.berryapi.net/?service=App.Bing.Story&AppKey=KIpvPXdids&id=%s");
  BING_STORY_API_HOST("https://cn.bing.com/cnhp/coverstory?d=%s");

  private String value;

  Constants(final String value) {
    this.value = value;
  }

  public String value() {
    return this.value;
  }
}
