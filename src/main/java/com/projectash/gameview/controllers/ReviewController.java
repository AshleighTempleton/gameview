package com.projectash.gameview.controllers;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import jakarta.validation.Valid;
import com.projectash.gameview.dtos.ReviewRequestDto;
import com.projectash.gameview.dtos.NexardaPriceWrapperDto;
import com.projectash.gameview.dtos.NexardaProductWrapperDto;
import com.projectash.gameview.dtos.ReviewDto;
import com.projectash.gameview.services.UserService;
import com.projectash.gameview.services.NexardaService;
import com.projectash.gameview.services.ReviewService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/game")
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;
    private final NexardaService nexardaService;


    @GetMapping("/{id}")
    public String getGameDetails(
        @PathVariable("id") Long id,
        Model model
    ){
        if(!populategameDetails(id, model)){
            return "searchResults";
        }
        ReviewRequestDto reviewForm = new ReviewRequestDto();
        model.addAttribute("review", reviewForm);
        reviewForm.setGameId(id);
        return "gameDetails";
    }

    @GetMapping("/{id}/edit/{reviewId}")
    public String editReviewInline(
        @PathVariable Long id,
        @PathVariable Long reviewId,
        Model model,
        Authentication authentication
    ) {
        if (!populategameDetails(id, model)) {
            return "searchResults";
        }
        ReviewDto review = reviewService.getReviewById(reviewId);
        model.addAttribute("reviewForm", review);
        model.addAttribute("editReviewId", reviewId);
        model.addAttribute("review", new ReviewDto()); // to allow the add review functionality to work
        return "gameDetails";
    }

    @PostMapping("/{id}/review")
    public String createReview(
        @PathVariable Long id,
        @ModelAttribute("review") @Valid ReviewRequestDto requestDto,
        Authentication authentication,
        Model model
    ) {
        try{
            Long userId = userService.getCurrentUserId();
            if (!reviewService.hasReviewByUserForGame(userId, id)) {
                reviewService.createReview(requestDto, userId);
            } else {
                model.addAttribute("error", "Review for this game already exists");
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION CONTROLLER: "+e.getMessage());
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/game/" + requestDto.getGameId();
    }

    @PostMapping("/{id}/update")
    public String updateReview(
        @PathVariable Long id,
        @ModelAttribute("review") @Valid ReviewRequestDto requestDto,
        Authentication authentication,
        Model model
    ) {
        try {
            reviewService.updateReview(id, requestDto);
        } catch (Exception e) {
            System.out.println("error: "+ e.getMessage());
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/game/" + requestDto.getGameId();
    }

    @PostMapping("/{id}/delete")
    public String deleteReview(
        @PathVariable Long id,
        @RequestParam("gameId") Long gameId,
        Authentication authentication,
        Model model
    ) {
        try {
            reviewService.deleteReview(id);
        } catch (Exception e) {
            System.out.println("error: "+ e.getMessage());
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/game/" + gameId;
    }

    private boolean populategameDetails(Long id, Model model){
        try
        {
            NexardaProductWrapperDto productWrapper = nexardaService.getProductDetails(id);
            if (productWrapper == null || productWrapper.getProduct() == null) {
                model.addAttribute("error", "Game not found or no details available.");
                return false;
            }
            model.addAttribute("game", productWrapper.getProduct());
            NexardaPriceWrapperDto priceWrapper = nexardaService.getPrices(id, "EUR");
            if (priceWrapper != null && priceWrapper.getPrices() != null) {
                model.addAttribute("prices", priceWrapper.getPrices());
            }
            Long currentUserId = userService.getCurrentUserId();
            model.addAttribute("currentUsername", userService.getUsernameByid(currentUserId));
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isLoggedIn = auth != null && auth.isAuthenticated() &&
                !(auth.getPrincipal() instanceof String && auth.getPrincipal().equals("anonymousUser"));
            model.addAttribute("isLoggedIn", isLoggedIn);
            List<ReviewDto> reviews = reviewService.getReviewsByGameId(id);
            model.addAttribute("reviews", reviews);
            model.addAttribute("hasUserReview", reviewService.hasReviewByUserForGame(currentUserId, id));
            model.addAttribute("rating", reviewService.getUserRatings(id));
            return true;
        } catch (Exception e) {
            System.out.println("Error retrieving game details: " + e.getMessage());
            model.addAttribute("error", "Error retrieving game details: " + e.getMessage());
            return false;
        }
    }
}
