package com.trilogyed.controller;

import com.trilogyed.exception.*;
import com.trilogyed.model.Customer;
import com.trilogyed.model.Invoice;
import com.trilogyed.model.LevelUp;
import com.trilogyed.model.Product;
import com.trilogyed.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CacheConfig(cacheNames = {"customers", "products", "levelups", "invoices" })
public class AdminController {

    @Autowired
    ServiceLayer service;

    // ===============================
    // ACCESSING THE CUSTOMER DATABASE
    // ===============================

    @GetMapping(value = "/admin/customers")
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getCustomers() {
        return service.findAllCustomers();
    }

    @Cacheable
    @GetMapping(value = "/admin/customer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Customer getCustomerById(@PathVariable Integer id) throws IdDoesNotExistException {
    if (service.findCustomerById(id) == null) {
        throw new IdDoesNotExistException("The customer information does not appear to be in our database. Please check the ID and try again.");
    } else {
        return service.findCustomerById(id);
    }


    }

    @CachePut(value = "#result.getId()")
    @PostMapping(value = "/admin/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer addNewCustomer(@RequestBody Customer customer) {
            return service.createNewCustomer(customer);

    }

    @CacheEvict(value = "#customer.getId()")
    @PutMapping(value = "/admin/customer/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@RequestBody Customer customer, @PathVariable Integer id) throws IdDoesNotExistException {
        if (service.findCustomerById(id) == null) {
            throw new IdDoesNotExistException("The customer information does not appear to be in our database. Please check the ID and try again.");
        } else {
            service.updateCustomer(customer);
        }
    }

    @CacheEvict
    @DeleteMapping(value = "/admin/customer/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Integer id) throws IdDoesNotExistException {
        if (service.findCustomerById(id) != null) {
            service.deleteCustomer(id);
        } else {
            throw new IdDoesNotExistException("The customer information does not appear to be in our database. Please check the ID and try again.");
        }
    }

    // ===============================
    // ACCESSING THE LEVEL UP DATABASE
    // ===============================

    @GetMapping(value = "/admin/levelups")
    @ResponseStatus(HttpStatus.OK)
    public List<LevelUp> getAllLevelUps(){
        return service.findAllLevelUps();
    }

    @Cacheable
    @GetMapping(value = "/admin/levelup/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LevelUp getLevelUpById(@PathVariable int id) throws ItemDoesNotExistRuntimeException {
        if (service.findLevelUpById(id) == null) {
            throw new ItemDoesNotExistRuntimeException("The Level Up account does not appear to exist. Please check the ID and try again.");
        }
        return service.findLevelUpById(id);
    }

    @GetMapping(value = "/admin/levelup/customer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LevelUp getLevelUpByCustomerId(@PathVariable int id) throws ItemDoesNotExistRuntimeException {
        if (service.findLevelUpByCustomerId(id) == null) {
            throw new ItemDoesNotExistRuntimeException("The customer information does not appear to be in our database. Please check the ID and try again.");
        }
        return service.findLevelUpByCustomerId(id);
    }

    @CachePut(value = "#result.getId()")
    @PostMapping(value = "/admin/levelup")
    @ResponseStatus(HttpStatus.CREATED)
    public LevelUp addLevelUp(@RequestBody LevelUp levelUp) throws CustomerAlreadyMemberException {
        if (service.findLevelUpById(levelUp.getCustomerId()) == null) {
            return service.createNewLevelUp(levelUp);
        } else {
            throw new CustomerAlreadyMemberException("The customer is already a member.");
        }

    }

    @CacheEvict(value = "#levelup.getId()")
    @PutMapping(value = "/admin/levelUp")
    public void updateLevelUp(@RequestBody LevelUp levelUp){
        service.updateLevelUp(levelUp);
    }

    // ===============================
    // ACCESSING THE INVOICE DATABASE
    // ===============================

    @GetMapping(value = "/admin/invoices")
    @ResponseStatus(HttpStatus.OK)
    public List<Invoice> getAllInvoices(){
        return service.findAllInvoices();
    }

    @Cacheable
    @GetMapping(value = "/admin/invoice/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Invoice getInvoiceById(@PathVariable int id) throws IdDoesNotExistException {
        if (service.findInvoiceById(id) == null) {
            throw new IdDoesNotExistException("The invoice you're looking for does not exist.");
        } else {
            return service.findInvoiceById(id);
        }

    }

    @GetMapping(value = "/admin/invoice/customer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Invoice> getInvoiceByCustomerId(@PathVariable int id) throws IdDoesNotExistException {
        if (service.findInvoicesByCustomerId(id) == null) {
            throw new IdDoesNotExistException("The invoice customer at this ID does not exist.");
        } else {
            return service.findInvoicesByCustomerId(id);
        }
    }

    @CachePut(value = "#result.getId()")
    @PostMapping(value = "/admin/invoice")
    @ResponseStatus(HttpStatus.CREATED)
    public Invoice addInvoice(@RequestBody Invoice invoice){
        return service.createNewInvoice(invoice);
    }

    @CacheEvict(value = "#invoice.getId()")
    @PutMapping(value = "/admin/invoice")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInvoice(@RequestBody Invoice invoice){
        service.updateInvoice(invoice);
    }

    @CacheEvict
    @DeleteMapping(value = "/admin/invoice/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoiceById(@PathVariable int id) throws IdDoesNotExistException {
        if (service.findInvoiceById(id) == null) {
            throw new IdDoesNotExistException("The invoice you're looking for does not exist");
        } else {
            service.deleteInvoice(id);
        }
    }

    // ===============================
    // ACCESSING THE PRODUCT DATABASE
    // ===============================

    @GetMapping(value = "/admin/products")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts() {return service.findAllProducts();}

    @Cacheable
    @GetMapping(value = "/admin/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProductById(@PathVariable int id) throws ItemDoesNotExistRuntimeException {
        if (service.findProductById(id) == null) {
            throw new ItemDoesNotExistRuntimeException("The product you're looking for does not exist");
        } else {
            return service.findProductById(id);
        }
    }

    @CachePut(value = "#result.getId()")
    @PostMapping(value = "/admin/product")
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody Product product){return service.createNewProduct(product);}

    @CacheEvict(value = "#product.getId()")
    @PutMapping(value = "/admin/product")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@RequestBody Product product){
        service.updateProduct(product);
    }

    @CacheEvict
    @DeleteMapping(value = "/admin/product/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable int id) throws IdDoesNotExistException {
        if (service.findProductById(id) == null) {
            throw new IdDoesNotExistException("The product you're looking for does not exist");
        } else {
            service.deleteProduct(id);
        }
    }

}
