package com.thungcam.chacalang.service;

import com.thungcam.chacalang.entity.Menu;
//import com.thungcam.chacalang.entity.Product;

import java.util.List;

public interface MenuService {
    List<Menu> getAllMenu();
    Menu getById(Long id);
    Menu save(Menu menu);
    void deleteById(Long id);
    List<Menu> filterMenu(Long categoryId, Integer minPrice, Integer maxPrice);
}

