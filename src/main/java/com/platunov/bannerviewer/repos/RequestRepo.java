package com.platunov.bannerviewer.repos;

import com.platunov.bannerviewer.domain.Banner;
import com.platunov.bannerviewer.domain.Request;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface RequestRepo extends CrudRepository<Request, Long> {
    List<Request> findAllByBannerAndAgentAndIpAndDateGreaterThan(
            Banner banner, String agent, String ip, Date afterDate);

}
