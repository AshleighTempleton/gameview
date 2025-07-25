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
        private List<NexardaGameResultDto> items;
        private String pagination;
    }
}
