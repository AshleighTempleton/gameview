package com.projectash.gameview.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Data
public class ReviewRequestDto {
    private Long id;
    @NotNull
    private Long gameId;
    @Min(1)
    @Max(10)
    private int rating;
    private Boolean recommended;
    @Size(max = 255)
    private String title;
    @Size(max = 1000)
    private String comment;
}
