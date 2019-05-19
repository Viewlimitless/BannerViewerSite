package com.platunov.bannerviewer.repos;

import com.platunov.bannerviewer.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepo extends CrudRepository<Category, Long> {

    List<Category> findByNameAndDeleted(String name, boolean deleted);

    List<Category> findByReqNameAndDeleted(String name, boolean deleted);

    List<Category> findAllByDeleted(boolean deleted);

    List<Category> findAllByNameContainsAndDeletedIsFalse(String name);

    Category findByReqNameContainsAndDeletedIsFalse(String name);

    Category findCategoryById(Long id);

    List<Category> findAllByDeletedAndNameAndIdIsNot(boolean deleted, String name, Long id);

    List<Category> findAllByDeletedAndReqNameAndIdIsNot(boolean deleted, String reqName, Long id);
}
