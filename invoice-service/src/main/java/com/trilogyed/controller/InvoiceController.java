package com.trilogyed.controller;

import com.trilogyed.model.Invoice;
import com.trilogyed.repository.InvoiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InvoiceController {

    @Autowired
    private InvoiceRepo invoiceRepo;

    @GetMapping(value = "/invoices")
    @ResponseStatus(HttpStatus.OK)
    public List<Invoice> getAllInvoices() {
        return invoiceRepo.findAll();
    }

    @GetMapping(value = "/invoice/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Invoice getInvoiceById(@PathVariable int id) {
        if (invoiceRepo.findById(id).isPresent()) {
            return invoiceRepo.getOne(id);
        }
        return null;
    }

    @GetMapping(value = "/invoice/customer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Invoice> getInvoiceByCustomerId(@PathVariable int id) {
        return invoiceRepo.findByCustomerId(id);
    }

    @PostMapping(value = "/invoice")
    @ResponseStatus(HttpStatus.CREATED)
    public Invoice addInvoice(@RequestBody Invoice invoice) {
        return invoiceRepo.save(invoice);
    }

    @PutMapping(value = "/invoice")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInvoice(@RequestBody Invoice invoice) {
        invoiceRepo.save(invoice);
    }

    @DeleteMapping(value = "/invoice/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoiceById(@PathVariable int id) {
        invoiceRepo.deleteById(id);
    }

    @DeleteMapping(value = "/invoice/batchDel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll() {
        invoiceRepo.deleteAll();
    }
}