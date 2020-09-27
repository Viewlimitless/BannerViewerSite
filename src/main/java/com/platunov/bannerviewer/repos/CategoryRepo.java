package com.platunov.bannerviewer.repos;

import com.platunov.bannerviewer.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepo extends CrudRepository<Category, Long> {

    List<Category> findAllByDeleted(boolean deleted);

    List<Category> findAllByNameContainsAndDeletedIsFalse(String name);

    List<Category> findByReqNameContainsAndDeletedIsFalse(String name);

    List<Category> findAllByDeletedAndNameAndIdIsNot(boolean deleted, String name, Long id);

    List<Category> findAllByDeletedAndReqNameAndIdIsNot(boolean deleted, String reqName, Long id);
}
