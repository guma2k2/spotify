package com.spotify.app.mapper;

import com.spotify.app.dto.response.PlaylistResponse;
import com.spotify.app.dto.response.ReviewResponse;
import com.spotify.app.model.Playlist;
import com.spotify.app.model.Review;
import com.spotify.app.model.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewResponseMapper {
    ReviewResponseMapper INSTANCE = Mappers.getMapper(ReviewResponseMapper.class);

    @Mapping(target = "createdAt", expression = "java(getCreatedAt(review))" , dateFormat = " MM/dd/yyyy hh:mm:ss")
    ReviewResponse reviewToReviewResponse(Review review);

    List<ReviewResponse> reviewsToReviewResponses(List<Review> reviews);

    default String getCreatedAt(Review review) {
        String pattern = "MM/dd/yyyy hh:mm:ss";
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);
        return review.getCreatedAt().format(dateFormat) ;
    }
}
