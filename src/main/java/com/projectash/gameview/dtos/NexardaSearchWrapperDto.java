package com.projectash.gameview.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
public class NexardaSearchWrapperDto {
    private boolean success;
    private String message;
    private Results results;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Results{
        private int page;
        private int pages;
        private int shown;
        private int total;
        private List<GameResult> items;
        private String pagination;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GameResult {
        private String type;
        private String title;
        private String text;
        private String slug;
        private String image;
        private Info game_info;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Info {
        private String id;
        private String name;
        private String short_desc;
        private long release_date;  // timestamp from JSON (you might convert later)
        private boolean upcoming;
        private boolean delisted;
        private boolean cancelled;
        private boolean spotlight;
        private Double lowest_price;
        private Integer highest_discount;
        private List<Integer> developers;
        private List<Integer> publishers;
        private List<Platform> platforms;
        private List<AgeRating> age_ratings;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Platform {
        private String name;
        private String slug;
        private String icon;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AgeRating {
        private String id;
        private String name;
    }
}
