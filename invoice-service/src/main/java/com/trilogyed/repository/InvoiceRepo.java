package com.trilogyed.repository;

import com.trilogyed.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepo extends JpaRepository<Invoice, Integer> {

    List<Invoice> findByCustomerId(Integer customerId);

}