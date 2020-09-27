package com.platunov.bannerviewer.repos;

import com.platunov.bannerviewer.domain.Banner;
import com.platunov.bannerviewer.domain.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface BannerRepo extends CrudRepository<Banner, Long> {
    String GET_BANNER_TO_VISITOR = "SELECT *\n" +
            "FROM (\n" +
            "       SELECT *\n" +
            "       FROM (SELECT t1.*, t2.date\n" +
            "             FROM (SELECT *\n" +
            "                   FROM banner\n" +
            "                   WHERE deleted = false\n" +
            "                     AND category_id = ?1) t1\n" +
            "                    LEFT JOIN\n" +
            "                  (SELECT *\n" +
            "                   FROM request\n" +
            "                   WHERE ip_address = ?2\n" +
            "                     AND user_agent =\n" +
            "                         ?3\n" +
            "                    AND date > ?4\n" +
            "                  ) t2\n" +
            "                  ON t2.banner_id = t1.id) t3\n" +
            "       WHERE  t3.date is null) t4\n" +
            "WHERE t4.price = (SELECT MAX(t5.price)\n" +
            "                  from (\n" +
            "                         SELECT *\n" +
            "                         FROM (SELECT t1.*, t2.date\n" +
            "                               FROM (SELECT *\n" +
            "                                     FROM banner\n" +
            "                                     WHERE deleted = false\n" +
            "                                       AND category_id = ?1) t1\n" +
            "                                      LEFT JOIN\n" +
            "                                    (SELECT *\n" +
            "                                     FROM request\n" +
            "                                     WHERE ip_address = ?2\n" +
            "                                       AND user_agent =\n" +
            "                                           ?3\n" +
            "                                       AND date > ?4\n" +
            "                                    ) t2\n" +
            "                                    ON t2.banner_id = t1.id) t3\n" +
            "                         WHERE  t3.date is null) t5);";

    List<Banner> findByNameAndDeleted(String name, boolean deleted);

    List<Banner> findAllByDeleted(boolean deleted);

    List<Banner> findAllByNameContainsAndDeletedIsFalse(String name);

    List<Banner> findAllByCategoryAndDeleted(Category category, boolean deleted);

    List<Banner> findAllByDeletedAndNameAndIdIsNot(boolean deleted, String name, Long id);

    @Query(value = GET_BANNER_TO_VISITOR,
            nativeQuery = true)
    Set<Banner> findBannersForVisitor(Long categoryId, String ipAddress, String userAgent, LocalDate afterDate);


}
