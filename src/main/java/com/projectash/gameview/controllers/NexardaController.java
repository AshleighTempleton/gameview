package com.projectash.gameview.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.projectash.gameview.dtos.NexardaProductWrapperDto;
import com.projectash.gameview.services.NexardaService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequiredArgsConstructor
public class NexardaController {
    private final NexardaService nexardaService;

    @GetMapping("/game/{id}")
    public String getGameDetails(
        @PathVariable("id") String id,
        Model model
    ){
        try
        {
            NexardaProductWrapperDto wrapper = nexardaService.getProductDetails(id);
            if (wrapper != null && wrapper.getProduct() != null) {
                model.addAttribute("game", wrapper.getProduct());
                // model.addAttribute("prices", wrapper.getPrices());

            } else {
                model.addAttribute("error", "Game not found or no details available.");
                return "searchResults";
            }
        } catch (Exception e) {
            System.out.println("Error retrieving game details: " + e.getMessage());
            model.addAttribute("error", "Error retrieving game details: " + e.getMessage());
            return "searchResults";
        }
        return "gameDetails";
    }


    @RequestMapping("/status")
    public String getApiStatus() {
        return nexardaService.getApiStatus().toString();
    }

    @RequestMapping("/search")
    public String searchGames(
        @RequestParam("query") String query,
        @RequestParam(value = "currency", defaultValue = "EUR") String currency,
        Model model) {
        try{
            if(query != null && !query.isBlank()){
                model.addAttribute("query", query);
                var searchResult = nexardaService.searchGames(query, currency);
                if (searchResult == null || searchResult.getResults() == null) {
                    return "searchResults";
                }
                if (!searchResult.getResults().getItems().isEmpty() && searchResult.getResults().getItems() != null) {
                    model.addAttribute("games", searchResult.getResults().getItems());
                }
            }
            else {
                model.addAttribute("query", query);
                model.addAttribute("error", "Must enter a valid search query.");
            }
        }
        catch(Exception e){
            model.addAttribute("error", "Error searching for games: " + e.getMessage());
        }
        return "searchResults";
    }

    @RequestMapping("/product")
    public String getProductDetails(String id) {
        return nexardaService.getProductDetails(id).toString();
    }

    @RequestMapping("/prices")
    public String getPrices(
        @RequestParam("id") String id,
        @RequestParam(value = "currency", defaultValue = "EUR") String currency
    ) {
        return nexardaService.getPrices(id, currency).toString();
    }

}
