package com.projectash.gameview.dtos;

import lombok.Data;

@Data
public class ReviewDto {
    private Long id;
    private Long gameId;
    private String username;
    private int rating;
    private Boolean recommended;
    private String title;
    private String comment;
    private String createdAt;
}
