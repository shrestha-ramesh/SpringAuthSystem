package com.product.controller;

import com.common.model.Products;

import com.product.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/car/{car}")
    public Products getCar(@PathVariable String car){
        return productService.getCar(car);
    }

    @GetMapping("/bike/{bike}")
    public Products getBike(@PathVariable String bike){
        return productService.getBike(bike);
    }
}
