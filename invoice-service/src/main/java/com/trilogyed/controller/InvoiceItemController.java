package com.trilogyed.controller;

import com.trilogyed.model.InvoiceItem;
import com.trilogyed.repository.InvoiceItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InvoiceItemController {

    @Autowired
    private InvoiceItemRepo invoiceItemRepo;

    @GetMapping(value = "/invoiceItems")
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceItem> getAllInvoiceItems() {
        return invoiceItemRepo.findAll();
    }

    @GetMapping(value =  "/invoiceItems/invoice")
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceItem> getInvoiceItemsByInvoiceId(@RequestParam(value = "id") String id) {
        Integer iD = Integer.parseInt(id);
        return invoiceItemRepo.findByInvoiceId(iD);
    }

    @GetMapping(value =  "/invoiceItems/product")
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceItem> getInvoiceItemsByProductId(@RequestParam(value = "id") String id) {
        Integer iD = Integer.parseInt(id);
        return invoiceItemRepo.findByProductId(iD);
    }

    @GetMapping(value = "/invoiceItem/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InvoiceItem getInvoiceItemById(@PathVariable int id) {
        if (invoiceItemRepo.findById(id).isPresent()) {
            return invoiceItemRepo.getOne(id);
        }
        return null;
    }

    @PostMapping(value = "/invoiceItem")
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceItem addInvoiceItem(@RequestBody InvoiceItem invoiceItem) {
        return invoiceItemRepo.save(invoiceItem);
    }

    @PutMapping(value = "/invoiceItem")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInvoiceItem(@RequestBody InvoiceItem invoiceItem) {
        invoiceItemRepo.save(invoiceItem);
    }

    @DeleteMapping(value = "/invoiceItem/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoiceItemById(@PathVariable int id) {
        invoiceItemRepo.deleteById(id);
    }

    @DeleteMapping(value = "/invoiceItem/batchDel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll() {
        invoiceItemRepo.deleteAll();
    }
}