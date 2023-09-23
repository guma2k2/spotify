package com.spotify.app.repository;

import com.spotify.app.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository  extends JpaRepository<Role, Integer> {


    @Query("""
            SELECT r
            FROM Role r
            Where r.name = :name
            """)
    Optional<Role> findByName(@Param("name")String name);
}
