package com.spotify.app.controller;


import com.spotify.app.dto.response.UserNoAssociationResponse;
import com.spotify.app.service.FollowerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/follower")
@AllArgsConstructor
public class FollowerController {


    private final FollowerService followerService;

    @GetMapping("/{currentUserId}/follow/{targetUserId}")
    @Operation(description =
            "currentUser follow targetUser"
    )
    public ResponseEntity<?> following(
            @PathVariable("currentUserId") Long currentUserId,
            @PathVariable("targetUserId") Long targetUserId
    ) {
        return ResponseEntity.ok().body(followerService.addFollowing(currentUserId,targetUserId));
    }

    @DeleteMapping("/{currentUserId}/cancel/{targetUserId}")
    @Operation(description =
            "currentUser unfollow targetUser"
    )
    public ResponseEntity<?> unFollowing(
            @PathVariable("currentUserId") Long currentUserId,
            @PathVariable("targetUserId") Long targetUserId
    ) {
        return ResponseEntity.ok().body(followerService.cancelFollowing(currentUserId,targetUserId));
    }
    @GetMapping("/{userId}/followings")
    @Operation(description =
            "find all followings by userId when search type `artists`"
    )
    public List<UserNoAssociationResponse> following(
            @PathVariable("userId") Long userId
    ) {
        return followerService.findAllFollowingsByUserId(userId);
    }


    @GetMapping("/is/{currentUserId}/followed/{targetUserId}")
    public ResponseEntity<Boolean> checkCurrentUserFollowedTargetUser(
            @PathVariable("currentUserId") Long currentUserId,
            @PathVariable("targetUserId") Long targetUserId
    ) {
        return ResponseEntity.
                ok().
                body(
                    followerService.checkCurrentUserFollowedTargetUser(currentUserId,targetUserId)
                );
    }

}
