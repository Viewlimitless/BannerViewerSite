package com.platunov.bannerviewer.service;

import com.platunov.bannerviewer.domain.Banner;
import com.platunov.bannerviewer.domain.Category;
import com.platunov.bannerviewer.repos.BannerRepo;
import com.platunov.bannerviewer.repos.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public List<Category> findAll() {
        return categoryRepo.findAllByDeleted(false);
    }

    @Override
    @Transactional
    public void save(Category category) {
            categoryRepo.save(category);
    }

    @Override
    public Optional<Category> getById(Long id){
        return categoryRepo.findById(id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<Category> optional = categoryRepo.findById(id);

        if (optional.isPresent()) {
            Category category = optional.get();

            category.setDeleted(true);

            categoryRepo.save(category);
        }
    }

    @Override
    @Transactional
    public List<Category> findAllByNameContains(String name) {
        return categoryRepo.findAllByNameContainsAndDeletedIsFalse(name);
    }

    @Override
    @Transactional
    public List<Banner> findBannersInCategory(Long categoryId) {
        return bannerRepo.findAllByCategoryAndDeleted(categoryId);
    }

    @Override
    @Transactional
    public boolean correctInstance(Category category) {
        if (category.getName() == null || category.getName().trim().isEmpty() || category.getName().length() > 255 ){
            return false;
        }else if (category.getReqname() == null || category.getReqname().trim().isEmpty() || category.getReqname().length() > 255){
            return false;
        } else if
        (categoryRepo.findAllByDeletedAndNameAndIdIsNot(
                        false,
                        category.getName(),
                        category.getId() == null ? -1 : category.getId()
                ).size() != 0) {
            return false;
        } else return categoryRepo.findAllByDeletedAndReqnameAndIdIsNot(
                    false,
                    category.getReqname(),
                    category.getId() == null ? -1 : category.getId()
            ).size() == 0;
    }
}
