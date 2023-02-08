package com.user.service;

import com.user.common.model.Products;
import com.user.model.product.ProductIdentity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Supplier;
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductIdentity productIdentity;
    private final ProductAPI productAPI;

    public Products getProductAPI(){
        int min = 10;
        int max = 20;
        Supplier<Integer> getInt = ()-> (int)(Math.random()*(max-min+1)+min);
        String productId = getInt.get() % 2 == 0 ? productIdentity.getBike() : productIdentity.getCar();
        HashMap<String, Supplier<Products>> getProducts = new HashMap<>(){{
            put(productIdentity.getCar(), ()-> productAPI.getBikeBrands());
            put(productIdentity.getBike(), ()-> productAPI.getCarBrands());
        }};
        return Optional.ofNullable(getProducts.get(productId)).orElseThrow(()-> new RuntimeException("Not Found")).get();
    }
}
