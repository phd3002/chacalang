package com.thungcam.chacalang.service;

import com.thungcam.chacalang.entity.Menu;
//import com.thungcam.chacalang.entity.Product;

import java.util.List;

public interface MenuService {
    List<Menu> getAllMenus();

    Menu getMenuById(Long id);

    List<Menu> getMenusByCategory(Long categoryId);

    void saveMenu(Menu menu);

    void deleteMenu(Long id);

    List<Menu> filterMenu(Long categoryId, Integer minPrice, Integer maxPrice);
}

