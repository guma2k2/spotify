package com.spotify.app.service;

import com.spotify.app.dto.ReviewDTO;
import com.spotify.app.dto.response.ReviewResponse;
import com.spotify.app.mapper.ReviewResponseMapper;
import com.spotify.app.model.Review;
import com.spotify.app.model.Song;
import com.spotify.app.model.User;
import com.spotify.app.repository.ReviewRepository;
import com.spotify.app.repository.SongRepository;
import com.spotify.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final ReviewResponseMapper reviewResponseMapper;

    private final UserRepository userRepository;
    private final SongRepository songRepository;
    public List<ReviewResponse> createReview(Long userId, Long songId, ReviewDTO request) {
        User user = userRepository.findById(userId).orElseThrow();
        Song song = songRepository.findById(songId).orElseThrow();
        Review review = Review.builder()
                .user(user)
                .song(song)
                .createdAt(LocalDateTime.now())
                .content(request.content())
                .build();
        reviewRepository.saveAndFlush(review);
        return findBySongId(songId);
    }

    public List<ReviewResponse> findBySongId(Long songId) {
        List<Review> reviews = reviewRepository.findBySongId(songId);
        return reviewResponseMapper.reviewsToReviewResponses(reviews);
    }

}
