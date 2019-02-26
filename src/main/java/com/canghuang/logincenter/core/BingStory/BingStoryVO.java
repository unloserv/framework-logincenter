package com.canghuang.logincenter.core.BingStory;

import java.util.LinkedHashMap;
import java.util.List;
import lombok.Data;

/**
 * @author cs
 * @date 2019/2/25
 * @description
 */
@Data
public class BingStoryVO {

  private BingStory primary;

  private List<BingStory> extra;

  public BingStoryVO build(LinkedHashMap<String, Object> storyMap) {
    if (storyMap != null) {
      this.primary = BingStory.build(
              (LinkedHashMap<String, String>) storyMap.get("primary"));
      this.extra = BingStory.buildExtra(
              (LinkedHashMap<String, String>) storyMap.get("asc"));
    }
    return this;
  }
}
