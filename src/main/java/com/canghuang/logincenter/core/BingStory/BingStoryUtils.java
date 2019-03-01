package com.canghuang.logincenter.core.BingStory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.canghuang.logincenter.core.Constants;
import com.canghuang.logincenter.utils.OkHttpUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author cs
 * @date 2019/2/22
 * @description
 */
@Slf4j
@Component
public class BingStoryUtils {

  @Autowired private OkHttpUtils okHttpUtils;

  private static final int MAX_STORY_NUMBER = 3;

  private Queue<BingStoryVO> storyQueue = new ArrayBlockingQueue<>(MAX_STORY_NUMBER);

  private LocalDate lastestDate;

  public Queue<BingStoryVO> getStoryQueue() {
    if (storyQueue.isEmpty()) {
      initQueue();
    } else {
      outDateCheck();
    }
    return storyQueue;
  }

  /** 初始化故事队列 */
  private void initQueue() {
    final LocalDate today = LocalDate.now();
    for (int i = MAX_STORY_NUMBER; i > 0; i--) {
      final LocalDate date = today.minusDays(MAX_STORY_NUMBER - i);
      final BingStoryVO storyVO = findStory(date);
      if (storyVO != null) {
        addStory(date, storyVO);
      }
    }
  }

  /** 故事过期检查 */
  private void outDateCheck() {
    LocalDate today = LocalDate.now();
    if (isOutDate(today)) {
      initQueue();
    }
  }

  /**
   * 故事队列是否过时
   *
   * @return
   */
  private boolean isOutDate(LocalDate date) {
    return lastestDate.isEqual(date) ? false : true;
  }

  /**
   * 根据日期查找小故事
   *
   * @param date
   * @return
   */
  private BingStoryVO findStory(LocalDate date) {
    final String dateString = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    final String storyString =
        okHttpUtils.sendGetRequest(
            String.format(Constants.BING_STORY_API_HOST.value(), dateString));
    final JSONObject response = JSON.parseObject(storyString);
    if (response.isEmpty() || response.getIntValue("ret") != 200) {
      return null;
    }
    final JSONObject story = response.getJSONObject("data");
    if (story.getInteger("code") != null) {
      return null;
    }
    return new BingStoryVO().build(story);
  }

  /**
   * 增加故事
   *
   * @param date
   * @param storyVO
   */
  private void addStory(LocalDate date, BingStoryVO storyVO) {
    final boolean flag = storyQueue.offer(storyVO);
    if (!flag) {
      storyQueue.poll();
      storyQueue.add(storyVO);
    }
    this.lastestDate = date;
  }
}
