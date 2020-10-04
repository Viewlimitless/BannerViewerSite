package com.platunov.bannerviewer.conroller;

import com.platunov.bannerviewer.domain.Banner;
import com.platunov.bannerviewer.domain.Category;
import com.platunov.bannerviewer.dto.CategoryDto;
import com.platunov.bannerviewer.mapper.CategoryMapper;
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
@RequestMapping("/category")
public class CategoryController {
    private CategoryService categoryService;
    private CategoryMapper categoryMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public String categoryList(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model) {

        model.addAttribute("categories"
                , getCategoriesByFilter(filter));
        model.addAttribute("filter", filter);

        return "categoryList";
    }

    private List<CategoryDto> getCategoriesByFilter(String filter) {
        if (filter != null && !filter.isEmpty()) {
            return categoryService.findAllByNameContains(filter).stream()
                    .map(categoryMapper::toDto)
                    .collect(Collectors.toList());
        } else {
            return categoryService.findAll().stream()
                    .map(categoryMapper::toDto)
                    .collect(Collectors.toList());
        }
    }

    @PostMapping
    public String add(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model
    ) {

        String editorTitle = "Create new Category";
        model.addAttribute("editorTitle", editorTitle);
        return categoryEditForm(new CategoryDto("new Category", "request", false)
                , editorTitle, filter, model);
    }

    @GetMapping("{category}")
    public String categoryEditForm(
            @PathVariable("category") String categoryId,
            @RequestParam(required = false, defaultValue = "") String myEditorTitle,
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model
    ){
        Optional<Category> optional = categoryService.getById(ProjectUtils.tryParseLong(categoryId));
        if (optional.isPresent() && !optional.get().isDeleted()) {
            return categoryEditForm(categoryMapper.toDto(optional.get()), myEditorTitle, filter, model);
        } else return categoryList(filter, model);
    }

    private String categoryEditForm(
            CategoryDto categoryDto,
            String myEditorTitle,
            String filter,
            Model model
    ) {
        model.addAttribute("category", categoryDto);
        model.addAttribute("categories", getCategoriesByFilter(filter));
        model.addAttribute("filter", filter);

        if (myEditorTitle.isEmpty()) {
            model.addAttribute("editorTitle", String.format("%s ID: %s", categoryDto.getName(), categoryDto.getId()));
        } else {
            model.addAttribute("editorTitle", myEditorTitle);
        }

        return "categoryEdit";
    }

    @PutMapping
    public String categorySave(
            @RequestParam String categoryName,
            @RequestParam String request,
            @RequestParam String categoryId,
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model
    ) {
        Category category;
        Long catId = ProjectUtils.tryParseLong(categoryId);
        if (catId.longValue() == -1) {
            category = new Category(categoryName, request, false);
        } else {
            Optional<Category> optional = categoryService.getById(catId);
            if (optional.isPresent()) {
                category = optional.get();
                category.setName(categoryName);
                category.setReqname(request);
            } else return "redirect:/category";
        }

        if (!categoryService.correctInstance(category)) {
            model.addAttribute("allertMessage", "Category name or request are already exists or incorrect");
            return categoryEditForm(categoryMapper.toDto(category), "", filter, model);
        }

        categoryService.save(category);

        return "redirect:/category";
    }

    @DeleteMapping
    public String deleteCategory(
            @RequestParam String categoryId,
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model
    ) {
        Long catId = ProjectUtils.tryParseLong(categoryId);
        if (catId.longValue() == -1) {
            return categoryList(filter, model);
        }

        List<Banner> banners = categoryService.findBannersInCategory(catId);
        if (banners.size() > 0) {
            StringBuilder mes = new StringBuilder("Several banners in this category: ");

            for (int i = 0; i < banners.size(); i++) {
                mes.append("ID:" + banners.get(i).getId() + " ");
            }

            model.addAttribute("allertMessage", mes);
            categoryEditForm(categoryId, "", filter, model);
            return "categoryEdit";
        }

        categoryService.deleteById(catId);
        return "redirect:/category";
    }

}
