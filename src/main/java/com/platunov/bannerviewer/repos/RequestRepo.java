package com.platunov.bannerviewer.repos;

import com.platunov.bannerviewer.domain.Request;
import org.springframework.data.repository.CrudRepository;

public interface RequestRepo extends CrudRepository<Request, Long> {

}
