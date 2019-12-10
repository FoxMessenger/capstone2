package com.trilogyed.repository;

import com.trilogyed.model.Invoice;
import com.trilogyed.model.InvoiceItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InvoiceItemRepoTest {

    @Autowired
    InvoiceRepo invoiceRepo;

    @Autowired
    InvoiceItemRepo itemRepo;

    @Before
    public void setUp() {
        itemRepo.deleteAll();
    }

    @Test
    public void shouldFindByInvoiceId() {
        Invoice invoice1 = invoiceRepo.save(new Invoice(1, LocalDate.of(2019, 01, 30)));
        Invoice invoice2 = invoiceRepo.save(new Invoice(1, LocalDate.of(2019, 03, 30)));

        InvoiceItem ii1 = itemRepo.save(new InvoiceItem(invoice1.getId(), 1, 10, BigDecimal.valueOf(10.99)));
        InvoiceItem ii2 = itemRepo.save(new InvoiceItem(invoice1.getId(), 1, 20, BigDecimal.valueOf(12.99)));
        InvoiceItem ii3 = itemRepo.save(new InvoiceItem(invoice1.getId(), 2, 5, BigDecimal.valueOf(15.99)));
        InvoiceItem ii4 = itemRepo.save(new InvoiceItem(invoice2.getId(), 2, 8, BigDecimal.valueOf(19.99)));

        List<InvoiceItem> invoiceItems1 = new ArrayList<InvoiceItem>(){{add(ii1); add(ii2); add(ii3);}};
        List<InvoiceItem> invoiceItems2 = new ArrayList<InvoiceItem>(){{add(ii4);}};

        assertEquals(invoiceItems1, itemRepo.findByInvoiceId(invoice1.getId()));
        assertEquals(invoiceItems2, itemRepo.findByInvoiceId(invoice2.getId()));
    }

    @Test
    public void shouldFindByProductId() {
        Invoice invoice1 = invoiceRepo.save(new Invoice(1, LocalDate.of(2019, 01, 30)));
        Invoice invoice2 = invoiceRepo.save(new Invoice(1, LocalDate.of(2019, 03, 30)));

        InvoiceItem ii1 = itemRepo.save(new InvoiceItem(invoice1.getId(), 1, 10, BigDecimal.valueOf(10.99)));
        InvoiceItem ii2 = itemRepo.save(new InvoiceItem(invoice1.getId(), 1, 20, BigDecimal.valueOf(12.99)));
        InvoiceItem ii3 = itemRepo.save(new InvoiceItem(invoice1.getId(), 2, 5, BigDecimal.valueOf(15.99)));
        InvoiceItem ii4 = itemRepo.save(new InvoiceItem(invoice2.getId(), 2, 8, BigDecimal.valueOf(19.99)));

        List<InvoiceItem> invoiceItems1 = new ArrayList<InvoiceItem>(){{add(ii1); add(ii2);}};
        List<InvoiceItem> invoiceItems2 = new ArrayList<InvoiceItem>(){{add(ii3); add(ii4);}};

        assertEquals(invoiceItems1, itemRepo.findByProductId(1));
        assertEquals(invoiceItems2, itemRepo.findByProductId(2));
    }
}