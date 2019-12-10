package com.trilogyed.util.feign;

import com.trilogyed.exception.DuplicateEmailException;
import com.trilogyed.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "customer-service")
public interface CustomerClient {

    @GetMapping(value = "/customers")
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getAllCustomers();

    @GetMapping(value = "/customer/{id}")
    public Customer getCustomerById(@PathVariable int id);

    @PostMapping(value = "/customer")
    public Customer addCustomer(@RequestBody Customer customer);

    @PutMapping(value = "/customer")
    public void updateCustomer(@RequestBody Customer customer);

    @DeleteMapping(value = "/customer/{id}")
    public void deleteCustomerById(@PathVariable int id);

}
