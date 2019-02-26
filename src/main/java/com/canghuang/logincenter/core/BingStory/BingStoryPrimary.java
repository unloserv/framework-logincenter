package com.canghuang.logincenter.core.BingStory;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author cs
 * @date 2019/2/25
 * @description
 */
@Data
public class BingStoryPrimary {

  @JSONField(name = "pid")
  private String pid;

  @JSONField(name = "title")
  private String title;

  @JSONField(name = "attr")
  private String attribute;

  @JSONField(name = "image")
  private String image;

  @JSONField(name = "story")
  private String story;

  @JSONField(name = "search")
  private String search;

  @JSONField(name = "provider")
  private String provider;

  @JSONField(name = "ctt")
  private String continent;

  @JSONField(name = "cty")
  private String country;

  @JSONField(name = "ct")
  private String city;

  @JSONField(name = "lon")
  private String longitude;

  @JSONField(name = "lat")
  private String latitude;

  @JSONField(name = "date")
  private String date;
}
