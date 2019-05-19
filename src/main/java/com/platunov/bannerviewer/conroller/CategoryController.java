package com.platunov.bannerviewer.conroller;

import com.platunov.bannerviewer.domain.Banner;
import com.platunov.bannerviewer.domain.Category;
import com.platunov.bannerviewer.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/category")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String categoryList(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model) {

        Iterable<Category> categories;

        categories = getCategoriesByFilter(filter);

        model.addAttribute("categories", categories);
        model.addAttribute("filter", filter);

        return "categoryList";
    }

    private Iterable<Category> getCategoriesByFilter(String filter) {
        Iterable<Category> categories;
        if (filter != null && !filter.isEmpty()) {
            categories = categoryService.findAllByNameContains(filter);
        } else {
            categories = categoryService.findAll();
        }
        return categories;
    }

    @PostMapping("add")
    public String add(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model
    ) {
        Category category = new Category("new Category", "request", true);

        categoryService.save(category);

        categoryEditForm(category, "Create new Category", filter, model);

        return "categoryEdit";
    }

    @GetMapping("{category}")
    public String categoryEditForm(
            @PathVariable Category category,
            @RequestParam(required = false, defaultValue = "") String myEditorTitle,
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model
    ) {
        model.addAttribute("category", category);
        model.addAttribute("categories", getCategoriesByFilter(filter));
        model.addAttribute("filter", filter);

        if (myEditorTitle.isEmpty()) {
            model.addAttribute("editorTitle", String.format("%s ID: %s", category.getName(), category.getId()));
        } else {
            model.addAttribute("editorTitle", myEditorTitle);
        }

        return "categoryEdit";
    }

    @PostMapping
    public String categorySave(
            @RequestParam String categoryName,
            @RequestParam String request,
            @RequestParam("categoryId") Category category,
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model
    ) {
        category.setName(categoryName);
        category.setReqName(request);


        if (!categoryService.correctInstance(category)) {
            model.addAttribute("allertMessage", "Category name or request are already exists or incorrect");
            categoryEditForm(category, "", filter, model);
            return "categoryEdit";
        }

        category.setDeleted(false);

        categoryService.save(category);

        return "redirect:/category";
    }

    @PostMapping("delete")
    public String deleteCategory(
            @RequestParam("categoryId") Category category,
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model
    ) {
        List<Banner> banners = categoryService.findBannersInCategory(category);
        if (banners.size() > 0) {
            StringBuilder mes = new StringBuilder("Several banners in this category: ");

            for (int i = 0; i < banners.size(); i++) {
                mes.append("ID:" + banners.get(i).getId() + " ");
            }


            model.addAttribute("allertMessage", mes);
            categoryEditForm(category, "", filter, model);
            return "categoryEdit";

        }

        categoryService.deleteById(category.getId());
        return "redirect:/category";
    }

}
