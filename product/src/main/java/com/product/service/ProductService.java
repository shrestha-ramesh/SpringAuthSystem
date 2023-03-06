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

    public Products getCar(){
        List<Product> product = (List<Product>) productRepository.findAll();
        return Products.builder().products(product).build();
    }

    public Products getBike(){
        List<Product> product = (List<Product>) productRepository.findAll();
        return Products.builder().products(product).build();
    }
}
