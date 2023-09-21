package com.spotify.app.repository;

import com.spotify.app.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {


    @Query(
        """
            SELECT c
            FROM Category c
            WHERE c.categoryParent is null
        """
    )
    Set<Category> listAllParent();



    @Query(
            """
                SELECT c
                FROM Category c
                LEFT JOIN FETCH c.playlists
                LEFT JOIN FETCH c.categoryParent p
                WHERE p.id = :parentId
            """
    )
    Set<Category> listAllChildByParenId(@Param("parentId") Integer parentId);



    @Query("""
            
            SELECT c
            FROM Category c
            LEFT JOIN FETCH c.playlists p
            WHERE c.id = :id
            
            """)
    Optional<Category> findByIdCustom(@Param("id") Integer id);
}
