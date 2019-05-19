package com.platunov.bannerviewer.service;

import com.platunov.bannerviewer.domain.Banner;
import com.platunov.bannerviewer.domain.Category;
import com.platunov.bannerviewer.domain.Request;
import com.platunov.bannerviewer.repos.BannerRepo;
import com.platunov.bannerviewer.repos.CategoryRepo;
import com.platunov.bannerviewer.repos.RequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Banner> findByName(String name) {
        return bannerRepo.findByNameAndDeleted(name, false);
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

        Category category = categoryRepo.findByReqNameContainsAndDeletedIsFalse(categoryRequest);

        List<Banner> banners = bannerRepo.findAllByCategoryEqualsAndDeletedIsFalseOrderByPriceDesc(category);

        Float max = Float.MIN_VALUE;
        for (int i = 0; i < banners.size(); i++) {
            if (max <= banners.get(i).getPrice().floatValue()) {
                max = banners.get(i).getPrice().floatValue();
            } else {
                banners.remove(i);
                i--;
            }
        }
        Banner banner;
        if (banners.size() == 0) {
            requestRepo.save(new Request(
                    null,
                    userAgent,
                    remoteAddr,
                    new Date()
            ));
            return null;
        }
        int random = (int) (Math.random() * banners.size());
        banner = banners.get(random);

        Date after = new Date();
        after.setDate(new Date().getDate() - 1);
        List<Request> requests = requestRepo.findAllByBannerAndAgentAndIpAndDateGreaterThan(
                banner,
                userAgent,
                remoteAddr,
                after
        );

        if (requests.size() > 0) {
            requestRepo.save(new Request(
                    null,
                    userAgent,
                    remoteAddr,
                    new Date()
            ));
            return null;
        } else {
            requestRepo.save(new Request(
                    banner,
                    userAgent,
                    remoteAddr,
                    new Date()
            ));
            return banner;
        }
    }

    @Override
    public List<Banner> findAllByNameContains(String name) {
        return bannerRepo.findAllByNameContainsAndDeletedIsFalse(name);
    }

    @Override
    public boolean correctInstance(Banner banner) {

        try {
            if (banner.getContent().isEmpty()) {
                return false;
            } else if (categoryRepo.findById(banner.getCategory().getId()).get().isDeleted()) {
                return false;
            } else
                return bannerRepo.findAllByDeletedAndNameAndIdIsNot(
                        false,
                        banner.getName(),
                        banner.getId()
                ).size() == 0;
        } catch (NullPointerException e) {
            return false;
        }
    }

}