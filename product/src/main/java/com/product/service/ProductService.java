package com.product.service;

import com.common.model.Product;
import com.common.model.Products;
import com.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Products getCar(String car){
        List<Product> product = productRepository.findByProduct(car);
        return Products.builder().products(product).build();
    }

    public Products getBike(String bike){
        List<Product> product = productRepository.findByProduct(bike);
        return Products.builder().products(product).build();
    }
}
