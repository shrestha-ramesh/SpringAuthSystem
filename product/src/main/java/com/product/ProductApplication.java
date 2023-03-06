package com.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.product.repository"})
@EntityScan(basePackages = {"com.common.model"})
public class ProductApplication {
    public static void main(String[] args){
        SpringApplication.run(ProductApplication.class, args);}
}
