package com.user.common.model;


import lombok.Setter;
import lombok.Getter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private int id;
    private String name;
    private String brand;

//    public Product(){
//
//    }
//    public Product(int id, String name, String brand) {
//        this.id = id;
//        this.name = name;
//        this.brand = brand;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getBrand() {
//        return brand;
//    }
//
//    public void setBrand(String brand) {
//        this.brand = brand;
//    }
}
