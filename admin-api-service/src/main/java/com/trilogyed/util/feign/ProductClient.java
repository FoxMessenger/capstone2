package com.trilogyed.util.feign;

import com.trilogyed.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping(value = "/products")
    public List<Product> getAllProducts();

    @GetMapping(value = "/product/{id}")
    public Product getProductById(@PathVariable int id);

    @PostMapping(value = "/product")
    public Product addProduct(@RequestBody Product product);

    @PutMapping(value = "/product")
    public void updateProduct(@RequestBody Product product);

    @DeleteMapping(value = "/product/{id}")
    public void deleteProductById(@PathVariable int id);
}
