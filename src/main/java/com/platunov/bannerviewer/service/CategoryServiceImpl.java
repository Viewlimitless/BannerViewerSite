package com.platunov.bannerviewer.service;

import com.platunov.bannerviewer.domain.Banner;
import com.platunov.bannerviewer.domain.Category;
import com.platunov.bannerviewer.repos.BannerRepo;
import com.platunov.bannerviewer.repos.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {


    private CategoryRepo categoryRepo;
    private BannerRepo bannerRepo;

    @Autowired
    public CategoryServiceImpl(CategoryRepo categoryRepo, BannerRepo bannerRepo) {
        this.categoryRepo = categoryRepo;
        this.bannerRepo = bannerRepo;
    }

    @Override
    public Iterable<Category> findAll() {
        return categoryRepo.findAllByDeleted(false);
    }

    @Override
    public List<Category> findByName(String name) {
        return categoryRepo.findByNameAndDeleted(name, false);
    }

    @Override
    public Category save(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Category> optional = categoryRepo.findById(id);

        if (optional.isPresent()) {
            Category category = optional.get();

            category.setDeleted(true);

            categoryRepo.save(category);
        }
    }

    @Override
    public List<Category> findAllByNameContains(String name) {
        return categoryRepo.findAllByNameContainsAndDeletedIsFalse(name);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepo.findCategoryById(id);
    }

    @Override
    public List<Category> findByRequest(String request) {
        return categoryRepo.findByReqNameAndDeleted(request, false);
    }

    @Override
    public List<Banner> findBannersInCategory(Category category) {
        return bannerRepo.findAllByCategoryAndDeleted(category, false);
    }

    @Override
    public boolean correctInstance(Category category) {
        if (category.getName().trim().isEmpty() || category.getReqName().trim().isEmpty()) {
            return false;
        } else if
        (categoryRepo.findAllByDeletedAndNameAndIdIsNot(
                        false,
                        category.getName(),
                        category.getId()).size() != 0) {
            return false;
        } else return categoryRepo.findAllByDeletedAndReqNameAndIdIsNot(
                        false,
                        category.getReqName(),
                        category.getId()).size() == 0;
    }
}
