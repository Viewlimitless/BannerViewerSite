package com.platunov.bannerviewer.service;

import com.platunov.bannerviewer.domain.Banner;
import com.platunov.bannerviewer.domain.Category;

import java.util.Collection;
import java.util.List;


public interface CategoryService {
    Iterable<Category> findAll();
    List<Category> findByName(String name);

    Category save(Category category);

    void deleteById(Long id);

    List<Category> findAllByNameContains(String name);

    Category findById(Long id);

    List<Category> findByRequest(String request);

    List<Banner> findBannersInCategory(Category category);

    boolean correctInstance(Category category);
}
