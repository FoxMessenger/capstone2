package com.trilogyed.util.feign;

import com.trilogyed.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping(value = "/products")
    List<Product> getAllProducts();

    @PostMapping(value = "/product")
    Product addProduct(@RequestBody Product product);

    @GetMapping(value = "/product/{id}")
    Product getProductById(@PathVariable int id);

    @PutMapping(value = "/product")
    void updateProduct(@RequestBody Product product);

    @DeleteMapping(value = "/batchDel")
    void deleteAll();
}