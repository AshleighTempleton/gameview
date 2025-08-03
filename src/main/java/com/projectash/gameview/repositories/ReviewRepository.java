package com.projectash.gameview.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projectash.gameview.entities.Review;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findById(Long id);
    List<Review> findByGameId(Long gameId);
    List<Review> findByUserId(Long userId);
    boolean existsById(Long id);
}
