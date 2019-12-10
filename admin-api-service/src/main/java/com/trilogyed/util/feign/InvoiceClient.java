package com.trilogyed.util.feign;

import com.trilogyed.model.Invoice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "invoice-service")
public interface InvoiceClient {

    @GetMapping(value = "/invoices")
    public List<Invoice> getAllInvoices();

    @GetMapping(value = "/invoice/{id}")
    public Invoice getInvoiceById(@PathVariable int id);

    @GetMapping(value = "/invoice/customer/{id}")
    public List<Invoice> getInvoiceByCustomerId(@PathVariable int id);

    @PostMapping(value = "/invoice")
    public Invoice addInvoice(@RequestBody Invoice invoice);

    @PutMapping(value = "/invoice")
    public void updateInvoice(@RequestBody Invoice invoice);

    @DeleteMapping(value = "/invoice/{id}")
    public void deleteInvoiceById(@PathVariable int id);
}
