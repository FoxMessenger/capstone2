package com.trilogyed.controller;

import com.trilogyed.model.Product;
import com.trilogyed.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
//@CacheConfig(cacheNames = "products")
public class ProductController {

    @Autowired
    private ProductRepo repo;

    @GetMapping(value = "/products")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    @GetMapping(value = "/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProductById(@PathVariable int id) throws Exception {
        if (repo.findById(id).isPresent()) {
            return repo.getOne(id);
        }
        return null;
    }

    @PostMapping(value = "/product")
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody Product product) {
        return repo.save(product);
    }

    @PutMapping(value = "/product")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@RequestBody Product product) {
        repo.save(product);
    }

    @DeleteMapping(value = "/product/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable int id) {
        repo.deleteById(id);
    }

    @DeleteMapping(value = "/batchDel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll() {
        repo.deleteAll();
    }
}