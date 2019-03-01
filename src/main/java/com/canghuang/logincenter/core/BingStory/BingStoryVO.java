package com.canghuang.logincenter.core.BingStory;

import com.alibaba.fastjson.JSONObject;
import java.time.LocalDate;
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

  private LocalDate date;

  public BingStoryVO build(JSONObject storyMap, LocalDate date) {
    if (storyMap != null) {
      this.primary = JSONObject.parseObject(storyMap.getString("primary"), BingStoryPrimary.class);
      this.extra = JSONObject.parseObject(storyMap.getString("asc"), BingStoryExtra.class);
      this.date = date;
    }
    return this;
  }
}
