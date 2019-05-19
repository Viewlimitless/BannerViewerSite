package com.platunov.bannerviewer.repos;

import com.platunov.bannerviewer.domain.Banner;
import com.platunov.bannerviewer.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BannerRepo extends CrudRepository<Banner, Long> {
    List<Banner> findByNameAndDeleted(String name, boolean deleted);

    List<Banner> findAllByDeleted(boolean deleted);

    List<Banner> findAllByNameContainsAndDeletedIsFalse(String name);

    List<Banner> findAllByCategoryEqualsAndDeletedIsFalseOrderByPriceDesc(Category category);

    List<Banner> findAllByCategoryAndDeleted(Category category, boolean deleted);

    List<Banner> findAllByDeletedAndNameAndIdIsNot(boolean deleted, String name, Long id);
}
