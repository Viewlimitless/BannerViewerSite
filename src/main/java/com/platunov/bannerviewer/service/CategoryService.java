package com.platunov.bannerviewer.service;

import com.platunov.bannerviewer.domain.Banner;
import com.platunov.bannerviewer.domain.Category;

import java.util.Collection;
import java.util.List;


public interface CategoryService {
    Iterable<Category> findAll();

    Category save(Category category);

    void deleteById(Long id);

    List<Category> findAllByNameContains(String name);

    List<Banner> findBannersInCategory(Category category);

    boolean correctInstance(Category category);
}
