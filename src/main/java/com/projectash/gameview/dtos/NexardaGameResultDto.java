package com.projectash.gameview.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NexardaGameResultDto {
    private String type;
    private String title;
    private String text;
    private String slug;
    private String image;
    private NexardaGameInfoDto game_info;
}
