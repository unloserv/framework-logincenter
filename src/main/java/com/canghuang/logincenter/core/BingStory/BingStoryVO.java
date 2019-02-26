package com.canghuang.logincenter.core.BingStory;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author cs
 * @date 2019/2/25
 * @description
 */
@Data
public class BingStoryVO {

  private BingStoryPrimary primary;

  private BingStoryExtra extra;

  public BingStoryVO build(JSONObject storyMap) {
    if (storyMap != null) {
      this.primary = JSONObject.parseObject(storyMap.getString("primary"), BingStoryPrimary.class);
      this.extra = JSONObject.parseObject(storyMap.getString("asc"), BingStoryExtra.class);
    }
    return this;
  }
}
