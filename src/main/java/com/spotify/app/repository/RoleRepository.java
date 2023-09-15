package com.spotify.app.repository;

import com.spotify.app.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository  extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String name);
}
