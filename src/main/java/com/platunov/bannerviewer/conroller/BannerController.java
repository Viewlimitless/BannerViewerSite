package com.platunov.bannerviewer.conroller;

import com.platunov.bannerviewer.domain.Banner;
import com.platunov.bannerviewer.domain.Category;
import com.platunov.bannerviewer.service.BannerService;
import com.platunov.bannerviewer.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/banner")
public class BannerController {

    private BannerService bannerService;
    private CategoryService categoryService;

    @Autowired
    public BannerController(BannerService bannerService, CategoryService categoryService) {
        this.bannerService = bannerService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String bannerList(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model) {
        Iterable<Banner> banners = getBannersByFilter(filter);

        model.addAttribute("banners", banners);
        model.addAttribute("filter", filter);
        return "bannerList";
    }

    private Iterable<Banner> getBannersByFilter(@RequestParam(required = false, defaultValue = "") String filter) {
        Iterable<Banner> banners;
        if (filter != null && !filter.isEmpty()) {
            banners = bannerService.findAllByNameContains(filter);
        } else {
            banners = bannerService.findAll();
        }
        return banners;
    }

    @PostMapping
    public String add(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model
    ) {
        Banner banner = bannerService.getInstance();

        if (banner != null) {
            bannerService.save(banner);

            bannerEditForm(banner, "Create new Banner", filter, model);

            return "bannerEdit";
        } else model.addAttribute("banners", bannerService.findAllByNameContains(filter));
        model.addAttribute("filter", filter);
        model.addAttribute("mainAllertMeeaage", "Create category first");
        return "bannerList";
    }

    @GetMapping("{banner}")
    public String bannerEditForm(
            @PathVariable Banner banner,
            @RequestParam(required = false, defaultValue = "") String myEditorTitle,
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model
    ) {

        model.addAttribute("banner", banner);
        model.addAttribute("banners", getBannersByFilter(filter));
        model.addAttribute("filter", filter);

        if (myEditorTitle.isEmpty()) {
            model.addAttribute("editorTitle", String.format("%s ID: %s", banner.getName(), banner.getId()));
        } else {
            model.addAttribute("editorTitle", myEditorTitle);
        }

        model.addAttribute("categories", categoryService.findAll());

        return "bannerEdit";
    }

    @PutMapping
    public String bannerSave(
            @RequestParam String bannerName,
            @RequestParam String content,
            @RequestParam String price,
            @RequestParam("categorySelected") Category category,
            @RequestParam("bannerId") Banner banner,
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model
    ) {

        banner.setName(bannerName);
        if (!content.isEmpty()){
            banner.setContent(content);
        }
        banner.setCategory(category);


        if (!tryParseFloat(price)) {
            model.addAttribute("allertMessage", "Changes are not applied. Price is incorrect");
            bannerEditForm(banner, "", filter, model);
            return "bannerEdit";
        }
        banner.setPriceFloat(Float.parseFloat(price));


        if (!bannerService.correctInstance(banner)) {
            model.addAttribute("allertMessage", "Changes are not applied. Banner name is already exist or\n" +
                    "text is empty or category already deleted");
            bannerEditForm(banner, "", filter, model);
            return "bannerEdit";
        }

        banner.setDeleted(false);

        bannerService.save(banner);

        return "redirect:/banner";
    }

    @DeleteMapping
    public String deleteBanner(
            @RequestParam("bannerId") Long banner
    ) {
        bannerService.deleteById(banner);
        return "redirect:/banner";
    }

    private boolean tryParseFloat(String value) {
        try {
            Float.parseFloat(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
