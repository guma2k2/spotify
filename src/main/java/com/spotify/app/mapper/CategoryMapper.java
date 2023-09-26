package com.spotify.app.mapper;

import com.spotify.app.dto.AlbumDTO;
import com.spotify.app.dto.CategoryDTO;
import com.spotify.app.model.Album;
import com.spotify.app.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;


@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper( CategoryMapper.class );
    CategoryDTO categoryToCategoryDTO(Category category);

    Set<CategoryDTO> categoriesToCategoriesDTO(Set<Category> categories);
}
