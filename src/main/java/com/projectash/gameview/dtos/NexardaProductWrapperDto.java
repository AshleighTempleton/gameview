package com.projectash.gameview.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NexardaProductWrapperDto {
    private boolean success;
    private String code;
    private String message;
    private NexardaProductDto product;
}
