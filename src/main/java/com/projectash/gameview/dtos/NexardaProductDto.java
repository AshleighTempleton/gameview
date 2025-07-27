package com.projectash.gameview.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NexardaProductDto {
    private String type;
    private int id;
    private String name;
    private String slug;
    private String short_desc;
    private Images images;
    private long release;
    private boolean upcoming;
    private List<NexardaDevPubDto> developers;
    private List<NexardaDevPubDto> publishers;
    private List<String> platforms;
    private List<String> systems;
    private Media media;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Images {
        private String cover;
        private String banner;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Media {
        private List<String> screenshots;
        private List<String> youtube_trailer_ids;
        private List<String> raw_trailer_urls;
    }
}
