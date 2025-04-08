package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.entity.Category;
import com.thungcam.chacalang.repository.CategoryRepository;
import com.thungcam.chacalang.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    // Implement the methods defined in the CategoryService interface
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<Category> getAllCategories() {
        // Logic to retrieve all categories from the database
        return categoryRepository.findAll(); // Replace with actual implementation
    }
}
