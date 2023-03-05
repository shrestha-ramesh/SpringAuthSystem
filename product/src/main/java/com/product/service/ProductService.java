package com.product.service;

import com.common.model.Product;
import com.common.model.Products;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    public Products getCar(){
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

    public Products getBike(){
        System.out.println("This is from Bike");
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
