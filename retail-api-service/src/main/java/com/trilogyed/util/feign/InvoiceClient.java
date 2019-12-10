package com.trilogyed.util.feign;

import com.trilogyed.model.Invoice;
import com.trilogyed.model.InvoiceItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@FeignClient(name = "invoice-service")
public interface InvoiceClient{

    @GetMapping(value = "/invoice/{id}")
    Invoice getInvoiceById(@PathVariable int id);

    @GetMapping(value = "/invoices")
    List<Invoice> getAllInvoices();

    @PostMapping(value = "/invoice")
    Invoice addInvoice(@RequestBody Invoice invoice);

    @GetMapping(value = "/invoiceItem/{id}")
    InvoiceItem getInvoiceItemById(@PathVariable int id);

    @GetMapping(value = "/invoice/customer/{id}")
    List<Invoice> getInvoiceByCustomerId(@PathVariable int id);

    @PostMapping(value = "/invoiceItem")
    InvoiceItem addInvoiceItem(@RequestBody InvoiceItem invoiceItem);

    @GetMapping(value =  "/invoiceItems/invoice")
    List<InvoiceItem> getInvoiceItemsByInvoiceId(@RequestParam(value = "id") String id);

    @DeleteMapping(value = "/invoice/batchDel")
    void deleteAllInvoices();

    @DeleteMapping(value = "/invoiceItem/batchDel")
    void deleteAllInvoiceItems();
}