package com.canghuang.logincenter.core.BingStory;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author cs
 * @date 2019/2/25
 * @description
 */
@Data
public class BingStoryExtra {

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

  @JSONField(name = "pid_2")
  private String pid2;

  @JSONField(name = "title_2")
  private String title2;

  @JSONField(name = "attr_2")
  private String attribute2;

  @JSONField(name = "image_2")
  private String image2;

  @JSONField(name = "story_2")
  private String story2;

  @JSONField(name = "search_2")
  private String search2;

  @JSONField(name = "date")
  private String date;
}
