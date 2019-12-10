package com.trilogyed.repository;

import com.trilogyed.model.Invoice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class InvoiceRepoTest {

    @Autowired
    InvoiceRepo repo;

//    @Before
//    public void setUp() {
//        repo.deleteAll();
//    }

    @Test
    public void shouldRetrieveByCustomerId() {
        Invoice invoice1 = new Invoice(1, LocalDate.of(2019, 01, 30));
        Invoice invoice2 = new Invoice(1, LocalDate.of(2019, 03, 30));
        Invoice invoice3 = new Invoice(2, LocalDate.of(2019, 01, 30));
        Invoice invoice4 = new Invoice(3, LocalDate.of(2019, 01, 30));
        repo.save(invoice1);
        repo.save(invoice2);
        repo.save(invoice3);
        repo.save(invoice4);

        List<Invoice> invoices1 = new ArrayList<Invoice>() {{add(invoice1); add(invoice2);}};
        List<Invoice> invoices2 = new ArrayList<Invoice>() {{add(invoice3);}};
        List<Invoice> invoices3 = new ArrayList<Invoice>() {{add(invoice4);}};
        invoice1.setId(1);
        invoice2.setId(2);
        invoice3.setId(3);
        invoice4.setId(4);

        assertEquals(invoices1, repo.findByCustomerId(invoice1.getCustomerId()));
        assertEquals(invoices2, repo.findByCustomerId(invoice3.getCustomerId()));
        assertEquals(invoices3, repo.findByCustomerId(invoice4.getCustomerId()));
    }
}