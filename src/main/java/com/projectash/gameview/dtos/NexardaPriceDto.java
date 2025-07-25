package com.projectash.gameview.dtos;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NexardaPriceDto {
    private String slug;
    private BigDecimal price;
    private String currency;
}
