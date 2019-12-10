package com.trilogyed.repository;

import com.trilogyed.model.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceItemRepo extends JpaRepository<InvoiceItem, Integer> {

    List<InvoiceItem> findByInvoiceId(Integer invoiceId);

    List<InvoiceItem> findByProductId(Integer productId);
}