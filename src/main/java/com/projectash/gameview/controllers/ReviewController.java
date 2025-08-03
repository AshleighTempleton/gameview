package com.projectash.gameview.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.projectash.gameview.services.UserService;

import org.springframework.ui.Model;
import jakarta.validation.Valid;

import com.projectash.gameview.dtos.ReviewRequestDto;
import com.projectash.gameview.dtos.ReviewDto;
import com.projectash.gameview.services.NexardaService;
import com.projectash.gameview.services.ReviewService;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;
    private final NexardaService nexardaService;

    @GetMapping("/game/{gameId}/edit/{reviewId}")
    public String editReviewInline(
        @PathVariable Long gameId,
        @PathVariable Long reviewId,
        Model model,
        Authentication authentication
    ) {
        try{
            ReviewDto review = reviewService.getReviewById(reviewId);
            model.addAttribute("reviewForm", review);
            model.addAttribute("editReviewId", reviewId);
            model.addAttribute("review", new ReviewDto()); // to allow the add review functionality to work
            model.addAttribute("game", nexardaService.getProductDetails(gameId).getProduct());
            model.addAttribute("reviews", reviewService.getReviewsByGameId(gameId));
            model.addAttribute("currentUsername", userService.getUsernameByid(userService.getCurrentUserId()));
            model.addAttribute("isLoggedIn", authentication != null && authentication.isAuthenticated());
            model.addAttribute("hasUserReview", reviewService.hasReviewByUserForGame(userService.getCurrentUserId(), gameId));
            System.out.println("MODEL: "+ model);
            var priceWrapper = nexardaService.getPrices(gameId, "EUR");
            if (priceWrapper != null) {
                model.addAttribute("prices", priceWrapper.getPrices());
            }
        } catch(Exception e){
            System.out.println("EXCEPTION RETRIEVING REVIEW: "+e.getMessage());
            model.addAttribute("error", e.getMessage());
        }
        return "gameDetails";
    }

    @PostMapping("/game/{id}/review")
    public String createReview(
        @PathVariable Long id,
        @ModelAttribute("review") @Valid ReviewRequestDto requestDto,
        Authentication authentication,
        Model model
    ) {
        try{
            System.out.println("IN REVIEW CONTROLLER");
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

    @PostMapping("game/{id}/update")
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

    @PostMapping("game/{id}/delete")
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

}
