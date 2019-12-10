package com.trilogyed.service;

import com.trilogyed.exception.ItemDoesNotExistRuntimeException;
import com.trilogyed.exception.DuplicateEmailException;
import com.trilogyed.model.Customer;
import com.trilogyed.model.Invoice;
import com.trilogyed.model.LevelUp;
import com.trilogyed.model.Product;
import com.trilogyed.util.feign.CustomerClient;
import com.trilogyed.util.feign.InvoiceClient;
import com.trilogyed.util.feign.LevelUpClient;
import com.trilogyed.util.feign.ProductClient;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ServiceLayerTest {

    private ServiceLayer service;
    private CustomerClient customerClient;
    private ProductClient productClient;
    private InvoiceClient invoiceClient;
    private LevelUpClient levelUpClient;

    @Before
    public void setUp() throws Exception{
        CustomerMockSetUp();
        ProductMockSetUp();
        LevelUpMockSetUp();
        InvoiceMockSetUp();
        service = new ServiceLayer(customerClient, invoiceClient, levelUpClient, productClient);
    }


    public void CustomerMockSetUp() {
        customerClient = mock(CustomerClient.class);

        Customer customer = new Customer("Ken", "Masters", "123 Main Street", "USA", "90009", "Ken@Master.info", "333-555-5551");
        customer.setId(1);
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        doReturn(customer).when(customerClient).getCustomerById(1);
        doReturn(customerList).when(customerClient).getAllCustomers();


        doThrow(new ItemDoesNotExistRuntimeException("Customer Id does not exist in the database")).when(customerClient).getCustomerById(10);
        doThrow(new DuplicateEmailException("This should display error message")).when(customerClient).addCustomer(customer);

    }

    public void ProductMockSetUp(){
        productClient = mock(ProductClient.class);

        Product product = new Product("Icecream", "Sweet Vanilla Caramel", new BigDecimal("9.50"), new BigDecimal("2.50"), 100);
        product.setId(1);
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        doReturn(product).when(productClient).getProductById(1);
        doReturn(productList).when(productClient).getAllProducts();

        doThrow(new ItemDoesNotExistRuntimeException("Product Item ID does not exist in the database")).when(productClient).getProductById(10);

    }

    public void LevelUpMockSetUp(){
        levelUpClient = mock(LevelUpClient.class);

        LevelUp levelUp = new LevelUp(1, 500, LocalDate.of(2019, 10, 9));
        levelUp.setId(1);
        List<LevelUp> levelUpList = new ArrayList<>();
        levelUpList.add(levelUp);

        doReturn(levelUp).when(levelUpClient).getLevelUpById(1);
        doReturn(levelUpList).when(levelUpClient).getAllLevelUps();

        doThrow(new ItemDoesNotExistRuntimeException("LevelUp Account ID does not exist in the database")).when(levelUpClient).getLevelUpById(10);
        doThrow(new ItemDoesNotExistRuntimeException("Customer Id does not exist in the database")).when(levelUpClient).getLevelUpByCustomerId(10);
    }

    public void InvoiceMockSetUp(){
        invoiceClient = mock(InvoiceClient.class);

        Invoice invoice = new Invoice(1, LocalDate.of(2019, 11,12));
        invoice.setId(1);
        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(invoice);

        doReturn(invoice).when(invoiceClient).getInvoiceById(1);
        doReturn(invoiceList).when(invoiceClient).getInvoiceByCustomerId(1);
        doReturn(invoiceList).when(invoiceClient).getAllInvoices();

        doThrow(new ItemDoesNotExistRuntimeException("Invoice with that ID does not exist in the database")).when(invoiceClient).getInvoiceById(10);
        doThrow(new ItemDoesNotExistRuntimeException("Invoice with that Customer ID does not exist in the database")).when(invoiceClient).getInvoiceByCustomerId(10);
    }

    @Test
    public void shouldGetCustomerById() {

        Customer customer = new Customer("Ken", "Masters", "123 Main Street", "USA", "90009", "Ken@Master.info", "333-555-5551");
        customer.setId(1);

        Customer returnVal = service.findCustomerById(1);

        assertEquals(customer, returnVal);
    }

    @Test
    public void shouldGetAllCustomers(){

        Customer customer = new Customer("Ken", "Masters", "123 Main Street", "USA", "90009", "Ken@Master.info", "333-555-5551");
        customer.setId(1);

        List<Customer> customers = new ArrayList<>();
        customers.add(customer);

        List<Customer> returnVal = service.findAllCustomers();
        assertEquals(customers, returnVal);
    }

    @Test
    public void shouldGetProductById() {

        Product product = new Product("Icecream", "Sweet Vanilla Caramel", new BigDecimal("9.50"), new BigDecimal("2.50"), 100);
        product.setId(1);

        Product returnVal = service.findProductById(1);

        assertEquals(product, returnVal);
    }

    @Test
    public void shouldGetAllProducts(){

        Product product = new Product("Icecream", "Sweet Vanilla Caramel", new BigDecimal("9.50"), new BigDecimal("2.50"), 100);
        product.setId(1);

        List<Product> products = new ArrayList<>();
        products.add(product);

        List<Product> returnVal = service.findAllProducts();
        assertEquals(products, returnVal);
    }

    @Test
    public void shouldGetLevelUpById() {

        LevelUp levelUp = new LevelUp(1, 500, LocalDate.of(2019, 10, 9));
        levelUp.setId(1);

        LevelUp returnVal = service.findLevelUpById(1);

        assertEquals(levelUp, returnVal);
    }

    @Test
    public void shouldGetAllLevelUps(){

        LevelUp levelUp = new LevelUp(1, 500, LocalDate.of(2019, 10, 9));
        levelUp.setId(1);

        List<LevelUp> levelUps = new ArrayList<>();
        levelUps.add(levelUp);

        List<LevelUp> returnVal = service.findAllLevelUps();
        assertEquals(levelUps, returnVal);
    }

    @Test
    public void shouldGetAnInvoiceById() {

        Invoice invoice = new Invoice(1, LocalDate.of(2019, 11,12));
        invoice.setId(1);

        Invoice returnVal = service.findInvoiceById(1);

        assertEquals(invoice, returnVal);
    }

    @Test
    public void shouldGetAllInvoices(){

        Invoice invoice = new Invoice(1, LocalDate.of(2019, 11,12));
        invoice.setId(1);

        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(invoice);


        List<Invoice> returnVal = service.findAllInvoices();
        assertEquals(invoiceList, returnVal);
    }

    @Test
    public void shouldGetInvoiceByCustomerId() {
        Invoice invoice = new Invoice(1, LocalDate.of(2019, 11,12));
        invoice.setId(1);

        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(invoice);


        List<Invoice> returnVal = service.findInvoicesByCustomerId(1);
        assertEquals(invoiceList, returnVal);
    }

    @Test (expected = DuplicateEmailException.class)
    public void shouldReturnErrorWhenDuplicateEmailIsProvided() throws DuplicateEmailException {

        Customer customer = new Customer("Ken", "Masters", "123 Main Street", "USA", "90009", "Ken@Master.info", "333-555-5551");
        customer.setId(1);
        Customer returnVal = service.createNewCustomer(customer);
    }

    @Test (expected = ItemDoesNotExistRuntimeException.class)
    public void shouldReturnErrorIdIsNotFound() throws ItemDoesNotExistRuntimeException {
        service.findCustomerById(10);
        service.findLevelUpById(10);
        service.findLevelUpByCustomerId(10);
        service.findInvoicesByCustomerId(10);
        service.findInvoicesByCustomerId(10);
        service.findProductById(10);


    }

}