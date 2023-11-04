package com.spotify.app.controller;

import com.spotify.app.dto.ReviewDTO;
import com.spotify.app.service.ReviewService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/review")
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    @PostMapping("/{userId}/review/in/{songId}")
    public ResponseEntity<?> createReview(
            @PathVariable("userId") Long userId,
            @PathVariable("songId") Long songId,
            @RequestBody ReviewDTO request
    ) {
        return ResponseEntity.ok().body(reviewService.createReview(userId,songId,request));
    }

    @PutMapping("/admin/update/status/{reviewId}")
    public ResponseEntity<?> updateStatus(
            @PathVariable("reviewId") Long reviewId
    ) {
        return ResponseEntity.ok().body(reviewService.updateStatus(reviewId));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(reviewService.findAll());
    }




}
