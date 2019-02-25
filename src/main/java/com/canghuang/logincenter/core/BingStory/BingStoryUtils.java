package com.canghuang.logincenter.core.BingStory;

import com.canghuang.logincenter.core.Constants;
import com.canghuang.logincenter.utils.OkHttpUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author cs
 * @date 2019/2/22
 * @description
 */
@Component
public class BingStoryUtils {

  @Autowired private OkHttpUtils okHttpUtils;

  private static final int MAX_STORY_NUMBER = 3;

  private Queue<String> storyQueue = new ArrayBlockingQueue<>(MAX_STORY_NUMBER);

  private LocalDate lastestDate;

  public Queue<String> getStoryQueue() {
    if (storyQueue.isEmpty()) {
      initQueue();
    }
    outDateCheck();
    return storyQueue;
  }

  /** 初始化故事队列 */
  private void initQueue() {
    final LocalDate today = LocalDate.now();
    final int days = storyQueue.size();
    for (int i = 0; i < days; i++) {
      addStory(today.minusDays(i));
    }
  }

  /** 故事过期检查 */
  private void outDateCheck() {
    LocalDate today = LocalDate.now();
    if (isOutDate(today)) {
      final long days = today.toEpochDay() - lastestDate.toEpochDay();
      if (days >= MAX_STORY_NUMBER) {
        initQueue();
      } else {
        for (int i = 0; i < days; i++) {
          addStory(lastestDate.plusDays(i + 1));
        }
      }
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
   * 增加故事
   *
   * @param date
   */
  private void addStory(LocalDate date) {
    final String dateString = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    final String storyString =
        okHttpUtils.sendGetRequest(
            String.format(Constants.BING_STORY_API_HOST.value(), dateString));
    storyQueue.add(storyString);
    this.lastestDate = date;
  }
}
