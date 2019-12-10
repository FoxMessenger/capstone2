package com.trilogyed.service;

import com.trilogyed.exception.InsufficientInventoryException;
import com.trilogyed.exception.QuantityValueException;
import com.trilogyed.model.*;
import com.trilogyed.util.feign.*;
import com.trilogyed.viewmodel.CustomerViewModel;
import com.trilogyed.viewmodel.InvoiceItemViewModel;
import com.trilogyed.viewmodel.InvoiceViewModel;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ServiceLayerTest {

    private CustomerClient customerClient;
    private InvoiceClient invoiceClient;
    private LevelUpClient levelUpClient;
    private ProductClient productClient;
    private Cart service;

    @Before
    public void setUp() {
        setUpCustomerClientMock();
        setUpInvoiceClientMock();
        setUpLevelUpClientMock();
        setUpProductClientMock();

        service = new Cart(customerClient, invoiceClient, levelUpClient, productClient);
    }

    @Test
    public void shouldAddPointsToLevelUp() throws Exception {
        Invoice invoice = new Invoice(1, 1, LocalDate.of(2018, 1, 30));

        LevelUp levelUp = service.assignPoints(59.99, invoice);
        int pointsGotten = levelUp.getPoints();

        assertEquals(160, pointsGotten); //Customer with id 1 already has 150 points
    }

    @Test
    public void shouldGetProductsWithInventory() {
        List<Product> products = service.findAllProducts();

        assertEquals(2, products.size());
        assertEquals(1, service.getProductsWithInventory().size());
    }

    @Test
    public void shouldGetProductsByInvoiceId() {
        Invoice invoice = service.findInvoiceById(1);

        List<Product> expectedProduct = new ArrayList<>();
        Product product = new Product();
        product.setId(1);
        expectedProduct.add(product);
        expectedProduct.add(product);

        List<Product> actualProduct = service.getProductsByInvoiceId(invoice.getId());

        assertEquals(expectedProduct, actualProduct);
    }

    @Test
    public void shouldMakeAnOrder() throws Exception {
        Map<Integer, Integer> productAndQuantity = new HashMap<Integer, Integer>() {{put(1, 10);}};
        Order order = new Order("jane@doe.edu", productAndQuantity, LocalDate.of(2018, 1, 30));
        Customer customerExpected = new Customer();
        customerExpected = customerClient.addCustomer(customerExpected);
        LevelUp levelUpExpected = new LevelUp();
        levelUpExpected = levelUpClient.addLevelUp(levelUpExpected);

        String name = customerExpected.getFirstName() + " " + customerExpected.getLastName();
        String address = customerExpected.getStreet() + " " + customerExpected.getCity() + " " + customerExpected.getZip();
        CustomerViewModel customerViewModel = new CustomerViewModel(name, address, customerExpected.getEmail(), customerExpected.getPhone());
        List<InvoiceItemViewModel> items = new ArrayList<>();
        Product product = productClient.getProductById(1);
        items.add(new InvoiceItemViewModel(product.getName(), product.getDescription(), product.getListPrice(), productAndQuantity.get(1)));

        InvoiceViewModel viewModelExpected =
                new InvoiceViewModel(customerViewModel, levelUpExpected);
        viewModelExpected.setItems(items);
        viewModelExpected.setTotal(129.9);
        viewModelExpected.setPurchaseDate(order.getPurchaseDate());

        assertEquals(viewModelExpected, service.makeAnOrder(order));
    }

    @Test(expected = QuantityValueException.class)
    public void shouldThrowQuantityValueExceptionWhenAttemptingToMakeAnOrder() throws Exception {
        Map<Integer, Integer> productAndQuantity = new HashMap<Integer, Integer>() {{put(1, 0);}};
        Order order = new Order("jane@doe.edu", productAndQuantity, LocalDate.of(2018, 1, 30));
        Customer customerExpected = new Customer();
        customerExpected = customerClient.addCustomer(customerExpected);
        LevelUp levelUpExpected = new LevelUp();
        levelUpExpected = levelUpClient.addLevelUp(levelUpExpected);

        String name = customerExpected.getFirstName() + " " + customerExpected.getLastName();
        String address = customerExpected.getStreet() + " " + customerExpected.getCity() + " " + customerExpected.getZip();
        CustomerViewModel customerViewModel = new CustomerViewModel(name, address, customerExpected.getEmail(), customerExpected.getPhone());
        List<InvoiceItemViewModel> items = new ArrayList<>();
        Product product = productClient.getProductById(1);
        items.add(new InvoiceItemViewModel(product.getName(), product.getDescription(), product.getListPrice(), productAndQuantity.get(1)));

        InvoiceViewModel viewModelExpected =
                new InvoiceViewModel(customerViewModel, levelUpExpected);
        viewModelExpected.setItems(items);
        viewModelExpected.setTotal(129.9);
        viewModelExpected.setPurchaseDate(order.getPurchaseDate());

        assertEquals(viewModelExpected, service.makeAnOrder(order));
    }

    @Test(expected = InsufficientInventoryException.class)
    public void shouldThrowInsufficientInventoryExceptionWhenAttemptingToMakeAnOrder() throws Exception {
        Map<Integer, Integer> productAndQuantity = new HashMap<Integer, Integer>() {{put(1, 101);}};
        Order order = new Order("jane@doe.edu", productAndQuantity, LocalDate.of(2018, 1, 30));
        Customer customerExpected = new Customer();
        customerExpected = customerClient.addCustomer(customerExpected);
        LevelUp levelUpExpected = new LevelUp();
        levelUpExpected = levelUpClient.addLevelUp(levelUpExpected);

        String name = customerExpected.getFirstName() + " " + customerExpected.getLastName();
        String address = customerExpected.getStreet() + " " + customerExpected.getCity() + " " + customerExpected.getZip();
        CustomerViewModel customerViewModel = new CustomerViewModel(name, address, customerExpected.getEmail(), customerExpected.getPhone());
        List<InvoiceItemViewModel> items = new ArrayList<>();
        Product product = productClient.getProductById(1);
        items.add(new InvoiceItemViewModel(product.getName(), product.getDescription(), product.getListPrice(), productAndQuantity.get(1)));

        InvoiceViewModel viewModelExpected =
                new InvoiceViewModel(customerViewModel, levelUpExpected);
        viewModelExpected.setItems(items);
        viewModelExpected.setTotal(129.9);
        viewModelExpected.setPurchaseDate(order.getPurchaseDate());

       service.makeAnOrder(order);
    }

    private void setUpCustomerClientMock() {
        customerClient = mock(CustomerClient.class);
        Customer customer = new Customer();
        Customer customerInDB = new Customer();
        customerInDB.setId(1);

        doReturn(customerInDB).when(customerClient).addCustomer(customer);
        doReturn(customerInDB).when(customerClient).getCustomerById(1);
        doReturn(customerInDB).when(customerClient).getCustomerByEmail("jane@doe.edu");
    }

    private void setUpInvoiceClientMock() {
        invoiceClient = mock(InvoiceClient.class);
        Invoice invoice = new Invoice(1, LocalDate.of(2018, 1, 30));
        Invoice invoiceInDb = new Invoice(1, LocalDate.of(2018, 1, 30));
        invoiceInDb.setId(1);
        InvoiceItem invoiceItem = new InvoiceItem(1, 1, 10, BigDecimal.valueOf(12.99));
        InvoiceItem invoiceItemInDb = new InvoiceItem(1, 1, 10, BigDecimal.valueOf(12.99));
        invoiceItemInDb.setId(1);

        InvoiceItem invoiceItemQuantityException = new InvoiceItem(1, 1, 0, BigDecimal.valueOf(12.99));
        InvoiceItem invoiceItemQuantityExceptionDB = new InvoiceItem(1, 1, 0, BigDecimal.valueOf(12.99));
        invoiceItemQuantityExceptionDB.setId(1);
        InvoiceItem invoiceItemInventoryException = new InvoiceItem(1, 1, 101, BigDecimal.valueOf(12.99));
        InvoiceItem invoiceItemInventoryExceptionDB = new InvoiceItem(1, 1, 101, BigDecimal.valueOf(12.99));
        invoiceItemInventoryExceptionDB.setId(1);
        List<InvoiceItem> invoiceItems = new ArrayList<InvoiceItem>() {{add(invoiceItemInDb); add(invoiceItemInDb);}};

        doReturn(invoiceInDb).when(invoiceClient).getInvoiceById(1);
        doReturn(invoiceInDb).when(invoiceClient).addInvoice(invoice);

        doReturn(invoiceItemInDb).when(invoiceClient).getInvoiceItemById(1);
        doReturn(invoiceItemInDb).when(invoiceClient).addInvoiceItem(invoiceItem);
        doReturn(invoiceItemInventoryExceptionDB).when(invoiceClient).addInvoiceItem(invoiceItemInventoryException);
        doReturn(invoiceItemQuantityExceptionDB).when(invoiceClient).addInvoiceItem(invoiceItemQuantityException);
        doReturn(invoiceItems).when(invoiceClient).getInvoiceItemsByInvoiceId("1");
    }

    private void setUpLevelUpClientMock() {
        levelUpClient = mock(LevelUpClient.class);
        LevelUp levelUp = new LevelUp();
        LevelUp levelUpInDb = new LevelUp();
        levelUpInDb.setId(1);

        doReturn(levelUpInDb).when(levelUpClient).addLevelUp(levelUp);
        doReturn(levelUpInDb).when(levelUpClient).retrieveLevelUpByCustomerId(1);
    }

    private void setUpProductClientMock() {
        productClient = mock(ProductClient.class);
        Product product = new Product();
        Product product1 = new Product();
        product1.setInventory(0);
        Product productInDb = product;
        Product product1InDb = product1;
        productInDb.setId(1);
        product1InDb.setId(2);
        List<Product> products = new ArrayList<Product>(){{add(productInDb); add(product1InDb);}};

        doReturn(productInDb).when(productClient).getProductById(1);
        doReturn(product1InDb).when(productClient).getProductById(2);
        doReturn(products).when(productClient).getAllProducts();
    }
}