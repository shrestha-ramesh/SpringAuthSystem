package com.user.module1.controller;

import com.user.common.model.Product;
import com.user.common.model.Products;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Home {


    @GetMapping("/module1")
    public Products Home(){
        List<Product> listProduct = new ArrayList<>();
        Product a = new Product();
        a.setId(1);
        a.setName("noodle");
        a.setBrand("rames");
        Product b = new Product(2,"noodle","ramen");
//        Product c = new Product(3,"noodle","ramen");
        listProduct.add(a);
//        listProduct.add(b);
//        listProduct.add(c);
        return Products.builder().products(listProduct).build();
    }
}
