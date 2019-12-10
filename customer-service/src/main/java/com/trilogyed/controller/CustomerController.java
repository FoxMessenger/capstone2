package com.trilogyed.controller;

import com.trilogyed.model.Customer;
import com.trilogyed.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
public class CustomerController {

    @Autowired
    private CustomerRepo repo;

    @GetMapping(value = "/customers")
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getAllCustomers() {
        return repo.findAll();
    }

    @GetMapping(value = "/customer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Customer getCustomerById(@PathVariable int id) throws Exception {
        if (repo.findById(id).isPresent()) {
            return repo.getOne(id);
        }
        return null;
    }

    @GetMapping(value = "/customer")
    @ResponseStatus(HttpStatus.OK)
    public Customer getCustomerByEmail(@RequestParam(value = "email") String email) {
        return repo.findByEmail(email);
    }

    @PostMapping(value = "/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer addCustomer(@RequestBody Customer customer) {
        return repo.save(customer);
    }

    @PutMapping(value = "/customer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@RequestBody Customer customer) {
        if (customer.getId() != null) {
            repo.save(customer);
        }
    }

    @DeleteMapping(value = "/customer/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomerById(@PathVariable int id) {
        repo.deleteById(id);
    }

    @DeleteMapping(value = "/batchDel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll() {
        repo.deleteAll();
    }
}