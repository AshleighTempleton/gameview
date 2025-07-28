package com.projectash.gameview.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NexardaPriceWrapperDto {
    private boolean success;
    private String message;
    private Info info;
    private Prices prices;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Info{
        private int id;
        private String name;
        private String slug;
        private String cover;
        private String banner;
        private long release;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Prices{
        private String currency;
        private String currency_symbol;
        private double lowest;
        private double highest;
        private int max_discount;
        private int stores;
        private int offers;
        private List<String> editions;
        private List<String> regions;
        private List<Offer> list;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Offer {
        private String url;
        private Store store;
        private String edition;
        private String edition_full;
        private String region;
        private boolean available;
        private double price;
        private int discount;
        private Coupon coupon;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Store {
        private String name;
        private String image;
        private String type;
        private boolean official;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Coupon {
        private boolean available;
        private int discount;
        private String code;
        private double price_without;
        private String terms;
    }
}