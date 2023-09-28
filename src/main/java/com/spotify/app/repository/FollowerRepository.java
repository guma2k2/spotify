package com.spotify.app.repository;

import com.spotify.app.model.Follower;
import com.spotify.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowerRepository extends JpaRepository<Follower,Long> {
    @Query("""
            SELECT f
            FROM Follower f
            JOIN FETCH f.followedUser
            JOIN FETCH f.followingUser fi
            JOIN FETCH fi.role
            WHERE fi.id=:id
            """)
    List<Follower> findByIdFollowingId(@Param("id") Long id);


    @Query("""
            DELETE
            FROM Follower f
            WHERE f.followingUser = :followingUser
            AND f.followedUser = :followedUser
            """)
    @Modifying
    void unfollowing(@Param("followingUser")User followingUser,
                         @Param("followedUser")User followedUser);
}
