package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.entity.Menu;
import com.thungcam.chacalang.repository.MenuRepository;
import com.thungcam.chacalang.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    @Override
    public Menu getMenuById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found with id: " + id));
    }

    @Override
    public List<Menu> getMenusByCategory(Long categoryId) {
        return menuRepository.findByCategoryId(categoryId);
    }

    @Override
    public void saveMenu(Menu menu) {
        menuRepository.save(menu);
    }

    @Override
    public void deleteMenu(Long id) {
        menuRepository.deleteById(id);
    }

    @Override
    public List<Menu> filterMenu(Long categoryId, Integer minPrice, Integer maxPrice) {
        Double min = (minPrice != null) ? minPrice.doubleValue() : null;
        Double max = (maxPrice != null) ? maxPrice.doubleValue() : null;
        return menuRepository.filterMenu(categoryId, min, max);
    }
}

