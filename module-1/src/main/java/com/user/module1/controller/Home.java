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


    @GetMapping("/car")
    public Products Home(){
        List<Product> listProduct = new ArrayList<>();
        Product a = new Product();
        a.setId(1);
        a.setName("Civic");
        a.setBrand("Honda");
        Product b = new Product(2,"Toyota","Corolla");
        Product c = new Product(3,"Hyundai","Venue");
        listProduct.add(a);
        listProduct.add(b);
        listProduct.add(c);
        return Products.builder().products(listProduct).build();
    }

    @GetMapping("/bike")
    public Products module(){
        List<Product> listProduct = new ArrayList<>();
        Product a = new Product();
        a.setId(1);
        a.setName("peanuts");
        a.setBrand("Planters");
        Product b = new Product(2,"Cashew","Wonderful");
        Product c = new Product(3,"Walnuts","Fisher");
        listProduct.add(a);
        listProduct.add(b);
        listProduct.add(c);
        return Products.builder().products(listProduct).build();
    }
}
