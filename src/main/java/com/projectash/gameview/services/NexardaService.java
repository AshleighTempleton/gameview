package com.projectash.gameview.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

import com.projectash.gameview.dtos.NexardaApiStatus;
import com.projectash.gameview.dtos.NexardaPriceDto;
import com.projectash.gameview.dtos.NexardaProductWrapperDto;
import com.projectash.gameview.dtos.NexardaSearchWrapperDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NexardaService {
    private final RestTemplate restTemplate;

    @Value("${NEXARDA_SEARCH}")
    private String searchUrl;

    @Value("${NEXARDA_PRODUCT}")
    private String productUrl;

    @Value("${NEXARDA_PRICES}")
    private String pricesUrl;

    @Value("${NEXARDA_IMAGE}")
    private String imageUrl;

    @Value("${NEXARDA_STATUS}")
    private String statusUrl;

    public NexardaApiStatus getApiStatus(){
        return restTemplate.getForObject(statusUrl, NexardaApiStatus.class);
    }

    public NexardaSearchWrapperDto searchGames(String query, String currency){
        try {
            URI uri = UriComponentsBuilder
            .newInstance()
            .uri(java.net.URI.create(searchUrl))
            .queryParam("type", "games")
            .queryParam("q", query)
            .queryParam("currency", currency)
            .build()
            .toUri();
            System.out.println("URI: " + uri);
            return restTemplate.getForObject(uri, NexardaSearchWrapperDto.class);
        } catch (Exception e) {
            System.err.println("Error searching for games: " + e.getMessage());
            throw new RuntimeException("Game search error: " + e.getMessage(), e);
        }
    }

    public NexardaProductWrapperDto getProductDetails(String id){
        try {
            URI uri = UriComponentsBuilder
            .newInstance()
            .uri(java.net.URI.create(productUrl))
            .queryParam("type", "game")
            .queryParam("id", id)
            .build()
            .toUri();
            return restTemplate.getForObject(uri, NexardaProductWrapperDto.class);
        } catch (Exception e) {
            System.err.println("Error retrieving product details: " + e.getMessage());
            throw new RuntimeException("Product details retrieval error: " + e.getMessage(), e);
        }
    }

    public NexardaPriceDto getPrices(String id, String currency){
        try {
            URI uri = UriComponentsBuilder
            .newInstance()
            .uri(java.net.URI.create(pricesUrl))
            .queryParam("currency", currency)
            .queryParam("id", id)
            .queryParam("type", "game")
            .build()
            .toUri();
            return restTemplate.getForObject(uri, NexardaPriceDto.class);
        } catch (Exception e) {
            System.err.println("Error retrieving prices: " + e.getMessage());
            throw new RuntimeException("Price retrieval error: " + e.getMessage(), e);
        }
    }
}
// do we need the random image? dto?