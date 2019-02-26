package com.canghuang.logincenter.core.BingStory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import lombok.Data;

/**
 * @author cs
 * @date 2019/2/25
 * @description
 */
@Data
public class BingStory {

  private String pid;
  
  private String title;

  private String attribute;

  private String image;

  private String story;

  private String search;

  private String provider;

  private String continent;

  private String country;

  private String city;

  private String longitude;

  private String latitude;

  private String date;

  public static BingStory build(LinkedHashMap<String, String> story) {
    BingStory bingStory = new BingStory();
    bingStory.setPid(story.get("pid"));
    bingStory.setTitle(story.get("title"));
    bingStory.setAttribute(story.get("attr"));
    bingStory.setImage(story.get("image"));
    bingStory.setStory(story.get("story"));
    bingStory.setSearch(story.get("search"));
    bingStory.setProvider(story.get("provider"));
    bingStory.setContinent(story.get("cct"));
    bingStory.setCountry(story.get("cty"));
    bingStory.setCity(story.get("ct"));
    bingStory.setLongitude(story.get("lon"));
    bingStory.setLatitude(story.get("lat"));
    bingStory.setDate(story.get("date"));
    return bingStory;
  }

  public static List<BingStory> buildExtra(LinkedHashMap<String, String> story) {
    List list = new ArrayList(2);
    BingStory bingStory = new BingStory();
    bingStory.setPid(story.get("pid"));
    bingStory.setTitle(story.get("title"));
    bingStory.setAttribute(story.get("attr"));
    bingStory.setImage(story.get("image"));
    bingStory.setStory(story.get("story"));
    bingStory.setSearch(story.get("search"));
    bingStory = new BingStory();
    bingStory.setPid(story.get("pid_2"));
    bingStory.setTitle(story.get("title_2"));
    bingStory.setAttribute(story.get("attr_2"));
    bingStory.setImage(story.get("image_2"));
    bingStory.setStory(story.get("story_2"));
    bingStory.setSearch(story.get("search_2"));
    return list;
  }
}
