package com.platunov.bannerviewer.conroller;

import com.platunov.bannerviewer.domain.Banner;
import com.platunov.bannerviewer.mapper.BannerMapper;
import com.platunov.bannerviewer.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@Controller
public class VisitorController {

    private BannerService bannerService;
    private BannerMapper bannerMapper;

    @Autowired
    public VisitorController(BannerService bannerService, BannerMapper bannerMapper) {
        this.bannerService = bannerService;
        this.bannerMapper = bannerMapper;
    }

    @GetMapping("/bid")
    public String visit(
            @RequestParam String category,
            Model model,
            HttpServletRequest request
    ) {


        Banner banner = bannerService.getInstance(
                category,
                request.getRemoteAddr(),
                request.getHeader("user-agent")
        );

        if (banner == null) {
            return "redirect:/bid/nocontent";
        }

        model.addAttribute("banner", bannerMapper.toDto(banner));

        return "visit";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/bid/nocontent")
    public void noContent(){
    }


}
