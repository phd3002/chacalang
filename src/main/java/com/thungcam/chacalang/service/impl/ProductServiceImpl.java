package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.entity.Product;
import com.thungcam.chacalang.repository.ProductRepository;
import com.thungcam.chacalang.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAllByOrderByNameAsc();
    }
}

