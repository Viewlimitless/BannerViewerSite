package com.platunov.bannerviewer.mapper;

import com.platunov.bannerviewer.domain.Banner;
import com.platunov.bannerviewer.dto.BannerDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class BannerMapper {

    @Autowired
    private ModelMapper mapper;

    public BannerDto toDto(Banner entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, BannerDto.class);
    }
}
