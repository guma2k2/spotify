package com.spotify.app.mapper;

import com.spotify.app.dto.response.CategoryResponseDTO;
import com.spotify.app.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryResponseMapper {

    CategoryResponseMapper INSTANCE = Mappers.getMapper(CategoryResponseMapper.class);
    CategoryResponseDTO category2CategoryResponseDTO(Category category);
}
