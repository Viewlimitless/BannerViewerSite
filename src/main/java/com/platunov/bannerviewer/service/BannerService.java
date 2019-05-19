package com.platunov.bannerviewer.service;

import com.platunov.bannerviewer.domain.Banner;

import java.util.List;

public interface BannerService {
    List<Banner> findByName(String name);

    Iterable<Banner> findAll();

    void save(Banner banner);

    void deleteById(Long id);

    Banner getInstance();

    Banner getInstance(String categoryRequest, String remoteAddr, String userAgent);

    List<Banner> findAllByNameContains(String name);

    boolean correctInstance(Banner banner);
}
