package com.product.products;

import com.common.model.Product;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
}
