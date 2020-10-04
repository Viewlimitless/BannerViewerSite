package com.platunov.bannerviewer.service;

import com.platunov.bannerviewer.domain.Banner;
import com.platunov.bannerviewer.domain.Category;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface CategoryService {
    List<Category> findAll();

    void save(Category category);

    Optional<Category> getById(Long id);

    void deleteById(Long id);

    List<Category> findAllByNameContains(String name);

    List<Banner> findBannersInCategory(Long categoryId);

    boolean correctInstance(Category category);
}
