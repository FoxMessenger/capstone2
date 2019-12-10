package com.trilogyed.service;

import com.trilogyed.exception.DuplicateEmailException;
import com.trilogyed.exception.ItemDoesNotExistRuntimeException;
import com.trilogyed.model.Customer;
import com.trilogyed.model.Invoice;
import com.trilogyed.model.LevelUp;
import com.trilogyed.model.Product;
import com.trilogyed.util.feign.CustomerClient;
import com.trilogyed.util.feign.InvoiceClient;
import com.trilogyed.util.feign.LevelUpClient;
import com.trilogyed.util.feign.ProductClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceLayer {
    @Autowired
    private CustomerClient customerClient;
    @Autowired
    private InvoiceClient invoiceClient;
    @Autowired
    private LevelUpClient levelUpClient;
    @Autowired
    private ProductClient productClient;

    public ServiceLayer() {
    }

    public ServiceLayer(CustomerClient customerClient, InvoiceClient invoiceClient, LevelUpClient levelUpClient, ProductClient productClient) {
        this.customerClient = customerClient;
        this.invoiceClient = invoiceClient;
        this.levelUpClient = levelUpClient;
        this.productClient = productClient;
    }

    // ====================
    // SERVICE THE CUSTOMER
    // ====================
    public List<Customer> findAllCustomers() {
        return customerClient.getAllCustomers();
    }

    public Customer findCustomerById(Integer id) throws ItemDoesNotExistRuntimeException {
        try  {
            return customerClient.getCustomerById(id);
        } catch (ItemDoesNotExistRuntimeException e){
            throw new ItemDoesNotExistRuntimeException("The customer information does not appear to be in our database. Please check the ID number and try again.");
        }

    }

    public Customer createNewCustomer(Customer customer) throws DuplicateEmailException {
        Customer customer1 = new Customer();
        try {
            customer1.setFirstName(customer.getFirstName());
            customer1.setLastName(customer.getLastName());
            customer1.setStreet(customer.getStreet());
            customer1.setCity(customer.getCity());
            customer1.setZip(customer.getZip());
            customer1.setPhone(customer.getPhone());
            customer1.setEmail(customer.getEmail());

            return customerClient.addCustomer(customer1);
        } catch (Exception e) {
            throw new DuplicateEmailException("Email is already being used, please try another email");
        }


    }

    public void updateCustomer(Customer customer) {customerClient.updateCustomer(customer);}

    public void deleteCustomer(Integer id) {
        customerClient.deleteCustomerById(id);
    }

    // ====================
    // SERVICE THE PRODUCT
    // ====================
    public List<Product> findAllProducts() {
        return productClient.getAllProducts();
    }

    public Product findProductById(Integer id) throws RuntimeException {
        return productClient.getProductById(id);
    }

    public Product createNewProduct(Product product) {
        Product product1 = new Product();
        product1.setName(product.getName());
        product1.setDescription(product.getDescription());
        product1.setUnitCost(product.getUnitCost());
        product1.setListPrice(product.getListPrice());
        product1.setInventory(product.getInventory());

        return productClient.addProduct(product1);
    }

    public void updateProduct(Product product) {productClient.updateProduct(product);}

    public void deleteProduct(Integer id) {
        productClient.deleteProductById(id);
    }

    // ====================
    // SERVICE THE LEVEL UP
    // ====================
    public List<LevelUp> findAllLevelUps() {
        return levelUpClient.getAllLevelUps();
    }

    public LevelUp findLevelUpById(Integer id) {
        return levelUpClient.getLevelUpById(id);
    }

    public LevelUp createNewLevelUp(LevelUp levelUp) {
        LevelUp levelup1 = new LevelUp();
        levelup1.setCustomerId(levelUp.getCustomerId());
        levelup1.setMemberDate(levelUp.getMemberDate());
        levelup1.setPoints(levelUp.getPoints());

        return levelUpClient.addLevelUp(levelup1);
    }

    public void updateLevelUp(LevelUp levelUp) {
        levelUpClient.updateLevelUp(levelUp);
    }

    public LevelUp findLevelUpByCustomerId(Integer id) {
        return levelUpClient.getLevelUpByCustomerId(id);
    }

    // ====================
    // SERVICE THE INVOICE
    // ====================
    public List<Invoice> findAllInvoices(){ return invoiceClient.getAllInvoices();}
    public List<Invoice> findInvoicesByCustomerId(Integer id) {
        return invoiceClient.getInvoiceByCustomerId(id);
    }
    public Invoice findInvoiceById(Integer id) {return invoiceClient.getInvoiceById(id);}
    public Invoice createNewInvoice(Invoice invoice) {
        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(invoice.getCustomerId());
        invoice1.setPurchaseDate(invoice.getPurchaseDate());

        return invoiceClient.addInvoice(invoice1);
    }

    public void updateInvoice(Invoice invoice) {invoiceClient.updateInvoice(invoice);}

    public void deleteInvoice(Integer id) {
        invoiceClient.deleteInvoiceById(id);
    }
}
