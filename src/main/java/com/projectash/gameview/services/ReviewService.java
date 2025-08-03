package com.projectash.gameview.services;

import org.springframework.stereotype.Service;
import com.projectash.gameview.repositories.ReviewRepository;

import lombok.RequiredArgsConstructor;

import com.projectash.gameview.entities.Review;
import com.projectash.gameview.dtos.ReviewDto;
import com.projectash.gameview.dtos.ReviewRequestDto;
import com.projectash.gameview.mappers.ReviewMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserService userService;

    public List<ReviewDto> getReviewsByGameId(Long gameId) {
        List<Review> reviews = reviewRepository.findByGameId(gameId);
        return reviews.stream()
            .map(review -> {
                ReviewDto dto = reviewMapper.toDto(review);
                dto.setUsername(userService.getUsernameByid(review.getUserId()));
                return dto;
            })
            .collect(Collectors.toList());
    }

    public ReviewDto getReviewById(Long id) {
        return reviewRepository.findById(id)
            .map(reviewMapper::toDto)
            .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    public boolean hasReviewByUserForGame(Long userId, Long gameId) {
        List<Review> userReviews = reviewRepository.findByUserId(userId);
        return userReviews.stream()
            .anyMatch(review -> review.getGameId().equals(gameId));
    }

    public ReviewDto createReview(ReviewRequestDto requestDto, Long userId){
        try {
            Review review = reviewMapper.toEntity(requestDto);
            review.setUserId(userId);
            review.setCreatedAt(LocalDateTime.now());
            Review savedReview = reviewRepository.save(review);
            System.out.println("REVIEW SAVED");
            return reviewMapper.toDto(savedReview);
        } catch (Exception e) {
            System.out.println("ERROR: "+e.getMessage());
            return null;
        }
    }

    public ReviewDto updateReview(Long reviewId, ReviewRequestDto requestDto){
        Review existingReview = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new RuntimeException("Review not found"));
        Long getCurrentUserId = userService.getCurrentUserId();
        if (!existingReview.getUserId().equals(getCurrentUserId)) {
            throw new SecurityException("Not authorised to update review");
        }
        reviewMapper.updateEntityFromDto(requestDto, existingReview);
        Review updatedReview = reviewRepository.save(existingReview);
        return reviewMapper.toDto(updatedReview);
    }

    public boolean deleteReview(Long reviewId){
        Optional<Review> review = reviewRepository.findById(reviewId);
        System.out.println("IN DELETE, FOUND REVIEW");
        if (review.isPresent() && review.get().getUserId().equals(userService.getCurrentUserId())) {
            reviewRepository.deleteById(reviewId);
            return true;
        }
        return false;
    }
}
