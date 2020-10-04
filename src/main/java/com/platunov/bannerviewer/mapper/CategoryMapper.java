package com.platunov.bannerviewer.mapper;

import com.platunov.bannerviewer.domain.Category;
import com.platunov.bannerviewer.dto.CategoryDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CategoryMapper {

    @Autowired
    private ModelMapper mapper;

    public CategoryDto toDto(Category entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, CategoryDto.class);
    }
}
