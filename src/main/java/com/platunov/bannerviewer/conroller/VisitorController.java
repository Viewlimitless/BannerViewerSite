package com.platunov.bannerviewer.conroller;

import com.platunov.bannerviewer.domain.Banner;
import com.platunov.bannerviewer.service.BannerService;
import com.platunov.bannerviewer.service.CategoryService;
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
    private CategoryService categoryService;

    @Autowired
    public VisitorController(BannerService bannerService, CategoryService categoryService) {
        this.bannerService = bannerService;
        this.categoryService = categoryService;
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

        model.addAttribute("banner", banner);

        return "visit";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/bid/nocontent")
    public void noContent(){
    }


}
