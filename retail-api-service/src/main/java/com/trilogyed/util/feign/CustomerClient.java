package com.trilogyed.util.feign;

import com.trilogyed.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "customer-service")
public interface CustomerClient {

    @GetMapping(value = "/customer/{id}")
    Customer getCustomerById(@PathVariable int id);

    @GetMapping(value = "/customer")
    Customer getCustomerByEmail(@RequestParam(value = "email") String email);

    @PostMapping(value = "/customer")
    Customer addCustomer(@RequestBody Customer customer);

    @PutMapping(value = "/customer")
    void updateCustomer(@RequestBody Customer customer);

    @DeleteMapping(value = "/batchDel")
    void deleteAll();
}