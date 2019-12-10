package com.trilogyed;

import com.trilogyed.model.*;
import com.trilogyed.util.feign.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class DbFiller {

    private CustomerClient customerClient;
    private ProductClient productClient;
    private InvoiceClient invoiceClient;
    private LevelUpClient levelUpClient;

    @Autowired
    public DbFiller(CustomerClient customerClient, ProductClient productClient, InvoiceClient invoiceClient, LevelUpClient levelUpClient) {
        this.customerClient = customerClient;
        this.productClient = productClient;
        this.invoiceClient = invoiceClient;
        this.levelUpClient = levelUpClient;
    }

    public Boolean fillDataBase() {
        customerClient.deleteAll();
        productClient.deleteAll();
        invoiceClient.deleteAllInvoices();
        invoiceClient.deleteAllInvoiceItems();
        levelUpClient.deleteAll();

        List<Customer> customers = new ArrayList<Customer>() {{
            add(new Customer());
            add(new Customer("Shawn", "Ford", "123 Main", "Chicago", "60627", "shawnf@email.com", "2132145"));
            add(new Customer("Sarah", "Ford", "123 Main", "Chicago", "60627", "sarahf@email.com", "2132145"));
            add(new Customer("Lisa", "Ford", "123 Main", "Chicago", "60627", "lisaf@email.com", "2132145"));
        }};
        List<Product> products = new ArrayList<Product>() {{
            add(new Product());
            add(new Product("Rock Em Sock Em", "Punching Robits", BigDecimal.valueOf(15.99), BigDecimal.valueOf(6.25), 200));
            add(new Product("Moon Boots", "Bounce Shoes", BigDecimal.valueOf(29.99), BigDecimal.valueOf(8.25), 500));
        }};

        customers.stream().forEach(customer -> customerClient.addCustomer(customer));
        products.stream().forEach(product -> productClient.addProduct(product));

        return true;
    }
}
