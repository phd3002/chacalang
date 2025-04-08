package com.thungcam.chacalang.service;

import com.thungcam.chacalang.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<Category> getAllCategories();
}
