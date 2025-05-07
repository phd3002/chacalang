package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.entity.Menu;
import com.thungcam.chacalang.repository.MenuRepository;
import com.thungcam.chacalang.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public List<Menu> getAllMenu() {
        return menuRepository.findAll();
    }

    @Override
    public Menu getById(Long id) {
        return menuRepository.findById(id).orElse(null);
    }

    @Override
    public Menu save(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public void deleteById(Long id) {
        menuRepository.deleteById(id);
    }

    @Override
    public List<Menu> filterMenu(Long categoryId, Integer minPrice, Integer maxPrice) {
        Double min = (minPrice != null) ? minPrice.doubleValue() : null;
        Double max = (maxPrice != null) ? maxPrice.doubleValue() : null;
        return menuRepository.filterMenu(categoryId, min, max);
    }

}

