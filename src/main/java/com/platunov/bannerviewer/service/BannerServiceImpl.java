package com.platunov.bannerviewer.service;

import com.platunov.bannerviewer.domain.Banner;
import com.platunov.bannerviewer.domain.Category;
import com.platunov.bannerviewer.domain.Request;
import com.platunov.bannerviewer.repos.BannerRepo;
import com.platunov.bannerviewer.repos.CategoryRepo;
import com.platunov.bannerviewer.repos.RequestRepo;
import com.platunov.bannerviewer.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BannerServiceImpl implements BannerService {
    private BannerRepo bannerRepo;
    private CategoryRepo categoryRepo;
    private RequestRepo requestRepo;

    @Autowired
    public BannerServiceImpl(BannerRepo bannerRepo, CategoryRepo categoryRepo, RequestRepo requestRepo) {
        this.bannerRepo = bannerRepo;
        this.categoryRepo = categoryRepo;
        this.requestRepo = requestRepo;
    }

    @Override
    public Iterable<Banner> findAll() {
        return bannerRepo.findAllByDeleted(false);
    }

    @Override
    public void save(Banner banner) {
        bannerRepo.save(banner);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Banner> optional = bannerRepo.findById(id);

        if (optional.isPresent()) {
            Banner banner = optional.get();

            banner.setDeleted(true);

            bannerRepo.save(banner);
        }
    }

    @Override
    public Banner getInstance() {
        Iterable<Category> categories = categoryRepo.findAllByDeleted(false);
        if (categories.iterator().hasNext()) {
            return new Banner(
                    "new Banner",
                    0.f,
                    categories.iterator().next(),
                    "input text",
                    true
            );
        } else return null;
    }

    @Override
    public Banner getInstance(String categoryRequest, String remoteAddr, String userAgent) {

        List <Category> categories = categoryRepo.findByReqNameContainsAndDeletedIsFalse(categoryRequest);
        Banner banner = null;

        if (categories.size() == 1) {

            Date afterDate = DateUtils.asDate(LocalDateTime.now().minusDays(1));
            Object[] banners = bannerRepo.findBannersForVisitor(
                    categories.get(0).getId(),
                    remoteAddr,
                    userAgent,
                    afterDate).toArray();

            if (banners.length > 0) {
                banner = (Banner) banners[(int) (Math.random() * banners.length)];
            }

        }

        requestRepo.save(new Request(
                banner,
                userAgent,
                remoteAddr,
                DateUtils.asDate(LocalDateTime.now())
        ));

        return banner;
    }

    @Override
    public List<Banner> findAllByNameContains(String name) {
        return bannerRepo.findAllByNameContainsAndDeletedIsFalse(name);
    }

    @Override
    public boolean correctInstance(Banner banner) {

        Optional<Category> optional = categoryRepo.findById(banner.getCategory().getId());
        if (banner.getContent().isEmpty()) {
            return false;
        } else if (!optional.isPresent()) {
            return false;
        } else if (optional.get().isDeleted()) {
            return false;
        }
        return bannerRepo.findAllByDeletedAndNameAndIdIsNot(
                false,
                banner.getName(),
                banner.getId()
        ).size() == 0;
    }

}
