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
    public List<Menu> getAllMenu() {
        return menuRepository.findAll();
    }
}

