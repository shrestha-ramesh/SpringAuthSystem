package com.product.controller;

import com.common.model.Products;

import com.product.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/car")
    public Products getCar(){
        return productService.getCar();
    }

    @GetMapping("/bike")
    public Products getBike(){
        return productService.getBike();
    }
}
