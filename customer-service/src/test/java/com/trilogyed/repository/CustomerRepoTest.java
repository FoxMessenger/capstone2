package com.trilogyed.repository;

import com.trilogyed.model.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerRepoTest {

    @Autowired
    CustomerRepo repo;

    @Before
    public void setUp() {
        repo.deleteAll();
    }

    @Test
    public void shouldGetCustomerByEmail() {
        Customer customer = new Customer("Ken", "Masters", "123 Main Street", "USA", "90009", "Ken@Master.info", "333-555-5551");
        repo.save(customer);

        Customer returnVal = repo.findByEmail("Ken@Master.info");
        assertEquals(customer, returnVal);
    }
}