package com.trilogyed.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.trilogyed.exception.NoSuchCustomerError;
import com.trilogyed.model.*;
import com.trilogyed.service.Cart;
import com.trilogyed.util.feign.LevelUpClient;
import com.trilogyed.viewmodel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RefreshScope
public class CartController {

    @Autowired
    private Cart cart;

    @PostMapping(value = "/order")
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceViewModel createInvoiceViewModel(@RequestBody Order order) throws Exception {
        return cart.makeAnOrder(order);
    }

    @GetMapping(value = "/invoices/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Invoice getInvoiceById(@PathVariable int id) {
        return cart.findInvoiceById(id);
    }

    @GetMapping(value = "/invoices")
    @ResponseStatus(HttpStatus.OK)
    public List<Invoice> getAllInvoices() {
        return cart.findAllInvoices();
    }

    @GetMapping(value = "/invoices/customer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Invoice> getInvoicesByCustomerId(@PathVariable int id) {
        return cart.findInvoiceByCustomerId(id);
    }

    @GetMapping(value = "/products/inventory")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProductsInInventory() {
        return cart.getProductsWithInventory();
    }

    @GetMapping(value = "/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProductById(@PathVariable int id) {
        return cart.findProductById(id);
    }

    @GetMapping(value = "/products/invoice/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProductByInvoiceId(@PathVariable int id) {
        return cart.getProductsByInvoiceId(id);
    }

    @HystrixCommand(fallbackMethod = "getLevelUpPoints2")
    @GetMapping(value = "/customer/points/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String getLevelUpPointsByCustomerId(@PathVariable Integer id) throws NoSuchCustomerError {
        LevelUp levelUp = cart.findLevelUpByCustomerId(id);
        return "You have earned " + levelUp.getPoints() + " points";
    }

    @HystrixCommand(fallbackMethod = "sorryMessage")
    public String getLevelUpPoints2(@PathVariable Integer id) throws NoSuchCustomerError {
        LevelUp levelUp = cart.findLevelUpByCustomerId(id);
        return "You have earned " + levelUp.getPoints() + " points";
    }

    public String sorryMessage(Integer id) {
        return "Unfortunately we're unable to retrieve your points at the moment. Sorry about the inconvenience :''''(";
    }
}