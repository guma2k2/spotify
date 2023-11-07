package com.spotify.app.repository;

import com.spotify.app.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {


    @Query(
        """
            SELECT c
            FROM Category c
            WHERE c.categoryParent is null AND c.status = true
        """
    )
    Set<Category> listAllParent();

    @Query("""
            SELECT c
            FROM Category c
            LEFT JOIN FETCH c.categoryParent
            """)
    List<Category> findAll();


    @Query("""
            SELECT c
            FROM Category c
            LEFT JOIN FETCH c.categoryParent cp
            WHERE c.id = :id AND c.status = true
            """)
    Optional<Category> findByIdWithParent(@Param("id") Integer id);


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


    @Query("""
            SELECT c
            FROM Category c
            LEFT JOIN FETCH c.categoryParent cp
            WHERE c.title = :title AND c.status = true
            """)
    Optional<Category> findByTitle(@Param("title") String title) ;
}
