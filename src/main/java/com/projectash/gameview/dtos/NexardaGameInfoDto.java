package com.projectash.gameview.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NexardaGameInfoDto {
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
    private List<PlatformDto> platforms;
    private List<AgeRatingDto> age_ratings;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PlatformDto {
        private String name;
        private String slug;
        private String icon;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AgeRatingDto {
        private String id;
        private String name;
    }
}
