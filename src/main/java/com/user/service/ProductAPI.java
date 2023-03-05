package com.user.service;

import com.common.model.Products;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class ProductAPI {

    private String url = "http://localhost:8081";

    public Products getCarBrands(){
        RestTemplate restTemplate = new RestTemplate();
        Products products;
        URI carUri = UriComponentsBuilder.fromUriString(url).path("/car").build().toUri();
        RequestEntity requestEntity = RequestEntity.get(carUri).header(HttpHeaders.AUTHORIZATION,"auth").build();
        try {
            ResponseEntity<Products> responseEntity = restTemplate.exchange(requestEntity, Products.class);
            products = responseEntity.getBody();
        }catch (HttpClientErrorException e){
            if(e.getRawStatusCode() == 404){
                throw new RuntimeException("Not Found");
            }else {
                throw new RuntimeException("Api Fail");
            }
        }
        return products;
    }
    public Products getBikeBrands(){
        RestTemplate restTemplate = new RestTemplate();
        Products products;
        URI carUri = UriComponentsBuilder.fromUriString(url).path("/bike").build().toUri();
        RequestEntity requestEntity = RequestEntity.get(carUri).header(HttpHeaders.AUTHORIZATION,"auth").build();
        try {
            ResponseEntity<Products> responseEntity = restTemplate.exchange(requestEntity, Products.class);
            products = responseEntity.getBody();
        }catch (HttpClientErrorException e){
            if(e.getRawStatusCode() == 404){
                throw new RuntimeException("Not Found");
            }else {
                throw new RuntimeException("Api Fail");
            }
        }
        return products;
    }
}
