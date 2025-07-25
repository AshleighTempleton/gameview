package com.projectash.gameview.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NexardaProductDto {
    private String id;
    private String title;
    private String slug;
    private String description;
    private List<String> platforms;
    private List<String> genres;
    private String developer;
    private String releaseDate;
    private String image;
    private String website; // really needed?
}
