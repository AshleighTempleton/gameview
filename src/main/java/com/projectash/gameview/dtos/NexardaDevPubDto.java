package com.projectash.gameview.dtos;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NexardaDevPubDto {
    private int id;
    private String name;
    private String slug;
}
