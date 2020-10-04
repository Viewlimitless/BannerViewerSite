package com.platunov.bannerviewer.conroller;

import com.platunov.bannerviewer.domain.Banner;
import com.platunov.bannerviewer.domain.Category;
import com.platunov.bannerviewer.dto.BannerDto;
import com.platunov.bannerviewer.mapper.BannerMapper;
import com.platunov.bannerviewer.service.BannerService;
import com.platunov.bannerviewer.service.CategoryService;
import com.platunov.bannerviewer.util.ProjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/banner")
public class BannerController {

    private BannerService bannerService;
    private CategoryService categoryService;
    private BannerMapper bannerMapper;

    @Autowired
    public BannerController(BannerService bannerService, CategoryService categoryService, BannerMapper bannerMapper) {
        this.bannerService = bannerService;
        this.categoryService = categoryService;
        this.bannerMapper = bannerMapper;
    }

    @GetMapping
    public String bannerList(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model) {
        List<BannerDto> banners = getBannersByFilter(filter);

        model.addAttribute("banners", banners);
        model.addAttribute("filter", filter);
        return "bannerList";
    }

    private List<BannerDto> getBannersByFilter(@RequestParam(required = false, defaultValue = "") String filter) {
        if (filter != null && !filter.isEmpty()) {
            return bannerService.findAllByNameContains(filter).stream()
                    .map(bannerMapper::toDto)
                    .collect(Collectors.toList());
        } else {
            return bannerService.findAll().stream()
                    .map(bannerMapper::toDto)
                    .collect(Collectors.toList());
        }
    }

    @PostMapping
    public String add(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model
    ) {
        BannerDto bannerDto = bannerMapper.toDto(bannerService.getInstance());

        if (bannerDto.getCategory() == null) {
            model.addAttribute("mainAlertMessage", "Create category first");
            return bannerList(filter, model);
        }

        return bannerEditForm(bannerDto, "Create new Banner", filter, model);
    }

    @GetMapping("{banner}")
    public String bannerEditForm(
            @PathVariable("banner") String bannerId,
            @RequestParam(required = false, defaultValue = "") String myEditorTitle,
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model
    ){
        Optional<Banner> optional = bannerService.getById(ProjectUtils.tryParseLong(bannerId));
        if(optional.isPresent() && !optional.get().isDeleted()){
            return bannerEditForm(bannerMapper.toDto(optional.get()), myEditorTitle, filter, model);
        }else return bannerList(filter, model);
    }

    private String bannerEditForm(
            BannerDto bannerDto,
            String myEditorTitle,
            String filter,
            Model model
    ) {

        model.addAttribute("banner", bannerDto);
        model.addAttribute("banners", getBannersByFilter(filter));
        model.addAttribute("filter", filter);

        if (bannerDto == null || bannerDto.isDeleted()) {
            return "bannerList";
        }

        if (myEditorTitle.isEmpty()) {
            model.addAttribute("editorTitle", String.format("%s ID: %s", bannerDto.getName(), bannerDto.getId()));
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
            @RequestParam("categorySelected") String categoryId,
            @RequestParam("bannerId") String bannerId,
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model
    ) {
        Long catId = ProjectUtils.tryParseLong(categoryId);
        Long banId = ProjectUtils.tryParseLong(bannerId);
        if (catId.longValue() == -1) {
            return "redirect:/banner";
        }
        Optional<Category> optionalCategory = categoryService.getById(catId);
        if (!optionalCategory.isPresent()) {
            return "redirect:/banner";
        }
        Banner banner;
        if (banId == -1) {
            banner = new Banner();
        } else {
            Optional<Banner> optionalBanner = bannerService.getById(banId);
            banner = optionalBanner.isPresent() ? optionalBanner.get() : new Banner();
        }
        banner.setCategory(optionalCategory.get());
        banner.setName(bannerName);
        banner.setContent(content);
        banner.setDeleted(false);

        try {
            banner.setPriceFloat(ProjectUtils.parseFloat(price));
        } catch (NumberFormatException e) {
            model.addAttribute("allertMessage", "Changes are not applied. Price is incorrect");
            bannerEditForm(bannerMapper.toDto(banner), "", filter, model);
            return "bannerEdit";
        }

        if (!bannerService.correctInstance(banner)) {
            model.addAttribute("allertMessage", "Changes are not applied. Banner name is already exist or\n" +
                    "text is empty or category already deleted");
            bannerEditForm(bannerMapper.toDto(banner), "", filter, model);
            return "bannerEdit";
        }

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
}
