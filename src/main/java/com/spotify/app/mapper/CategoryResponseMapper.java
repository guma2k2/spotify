package com.spotify.app.mapper;

import com.spotify.app.dto.response.CategoryResponse;
import com.spotify.app.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryResponseMapper {

    CategoryResponseMapper INSTANCE = Mappers.getMapper(CategoryResponseMapper.class);
    CategoryResponse category2CategoryResponse(Category category);
}
