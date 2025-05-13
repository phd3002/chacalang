package com.thungcam.chacalang.controller.admin;

import com.thungcam.chacalang.entity.Menu;
import com.thungcam.chacalang.service.CategoryService;
import com.thungcam.chacalang.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/menu")
public class AdminMenuController {

    final private MenuService menuService;

    final private CategoryService categoryService;

    @Autowired
    public AdminMenuController(MenuService menuService, CategoryService categoryService) {
        this.menuService = menuService;
        this.categoryService = categoryService;
    }
    @GetMapping
    public String listMenu(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            Model model) {

        model.addAttribute("menus", menuService.filterMenu(categoryId, minPrice, maxPrice));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("activePage", "menu");

        return "admin/menu-list";
    }


    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("menu", new Menu());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("activePage", "menu"); // 👈 thêm
        return "admin/menu-form";
    }


    @PostMapping("/save")
    public String save(@ModelAttribute("menu") Menu menu) {
        menuService.saveMenu(menu);
        return "redirect:/admin/menu";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("menu", menuService.getMenuById(id));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("activePage", "menu"); // 👈 thêm
        return "admin/menu-form";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return "redirect:/admin/menu";
    }
}

