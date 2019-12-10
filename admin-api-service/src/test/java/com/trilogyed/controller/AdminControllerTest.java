package com.trilogyed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.model.Customer;
import com.trilogyed.model.Invoice;
import com.trilogyed.model.LevelUp;
import com.trilogyed.model.Product;
import com.trilogyed.service.ServiceLayer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceLayer service;

    @MockBean
    private DataSource dataSource;

    private JacksonTester<Customer> jsonCustomer;
    private JacksonTester<List<Customer>> jsonCustomerList;

    private JacksonTester<Product> jsonProduct;
    private JacksonTester<List<Product>> jsonProductList;

    private JacksonTester<Invoice> jsonInvoice;
    private JacksonTester<List<Invoice>> jsonInvoiceList;

    private JacksonTester<LevelUp> jsonLevelUp;
    private JacksonTester<List<LevelUp>> jsonLevelUpList;

    @Before
    public void setUp() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    // ==================================================== //
    //                  CUSTOMER TESTS                      //
    // ==================================================== //

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = {"ADMIN"})
    public void shouldRetrieveAllCustomers_AndGetCustomerById() throws Exception {
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        customer1.setId(1);
        customer2.setId(2);
        List<Customer> customers = new ArrayList<Customer>() {{
            add(customer1);
            add(customer2);
        }};

        given(service.findAllCustomers())
                .willReturn(customers);

        MockHttpServletResponse allResponse = mockMvc.perform(
                get("/admin/customers").with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(allResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(allResponse.getContentAsString()).isEqualTo(jsonCustomerList.write(customers).getJson());

        given(service.findCustomerById(1))
                .willReturn(customer1);

        MockHttpServletResponse id1Response = mockMvc.perform(
                get("/admin/customer/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(id1Response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(id1Response.getContentAsString()).isEqualTo(jsonCustomer.write(customer1).getJson());
    }


    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = {"ADMIN"})
    public void shouldCreateCustomers() throws Exception {
        Customer customer1 = new Customer();
        Customer customerAdded = customer1;
        customerAdded.setId(1);
        given(service.createNewCustomer(customer1))
                .willReturn(customerAdded);
        MockHttpServletResponse createdResponse = mockMvc.perform(
                post("/admin/customer").with(csrf())
                        .content(jsonCustomer.write(customer1).getJson())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(createdResponse.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(createdResponse.getContentAsString()).isEqualTo(jsonCustomer.write(customerAdded).getJson());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = {"ADMIN"})
    public void shouldUpdateCustomers() throws Exception {
        Customer customer1 = new Customer();
        Customer customerAdded = customer1;
        customerAdded.setId(1);
        Customer customer2 = new Customer();
        customer2.setId(1);

        when(service.findCustomerById(1)).thenReturn(customer2);

        MockHttpServletResponse updateResponse = mockMvc.perform(
                put("/admin/customer/{id}", 1).with(csrf())
                        .content(jsonCustomer.write(customer2).getJson())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(updateResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        verify(service, times(1)).updateCustomer(customer2);
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = {"ADMIN"})
    public void shouldDeleteCustomers() throws Exception {
        Customer customer1 = new Customer();
        Customer customerAdded = customer1;
        customerAdded.setId(1);

        when(service.findCustomerById(1)).thenReturn(customerAdded);

        MockHttpServletResponse deleteResponse = mockMvc.perform(
                delete("/admin/customer/{id}", 1).with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(deleteResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        verify(service, times(1)).deleteCustomer(customer1.getId());
    }

    // ==================================================== //
    //                  LEVEL UP TESTS                      //
    // ==================================================== //


    // ==================================================== //
    //                  PRODUCT TESTS                       //
    // ==================================================== //
    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = {"ADMIN"})
    public void shouldRetrieveAllProducts() throws Exception {
        Product product1 = new Product();
        Product product2 = new Product();
        product1.setId(5);
        product2.setId(6);
        List<Product> products = new ArrayList<Product>() {{
            add(product1);
            add(product2);
        }};
        given(service.findAllProducts())
                .willReturn(products);
        MockHttpServletResponse allResponse = mockMvc.perform(
                get("/admin/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(allResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(allResponse.getContentAsString()).isEqualTo(jsonProductList.write(products).getJson());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = {"ADMIN"})
    public void shouldRetrieveProductById() throws Exception {
        Product product1 = new Product();
        product1.setId(1);
        given(service.findProductById(1))
                .willReturn(product1);
        MockHttpServletResponse id1Response = mockMvc.perform(
                get("/admin/product/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(id1Response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(id1Response.getContentAsString()).isEqualTo(jsonProduct.write(product1).getJson());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = {"ADMIN"})
    public void shouldCreateProducts() throws Exception {
        Product product1 = new Product();
        Product productAdded = product1;
        productAdded.setId(1);
        given(service.createNewProduct(product1))
                .willReturn(productAdded);
        MockHttpServletResponse createdResponse = mockMvc.perform(
                post("/admin/product").with(csrf())
                        .content(jsonProduct.write(product1).getJson())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(createdResponse.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(createdResponse.getContentAsString()).isEqualTo(jsonProduct.write(productAdded).getJson());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = {"ADMIN"})
    public void shouldUpdateProducts() throws Exception {
        Product product1 = new Product();
        Product productAdded = product1;
        productAdded.setId(1);

        Product product2 = new Product();
        product2.setId(1);

        when(service.findProductById(1)).thenReturn(product2);


        MockHttpServletResponse updateResponse = mockMvc.perform(
                put("/admin/product").with(csrf())
                        .content(jsonProduct.write(product2).getJson())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(updateResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        verify(service, times(1)).updateProduct(product2);
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = {"ADMIN"})
    public void shouldDeleteProducts() throws Exception {
        Product product1 = new Product();
        Product productAdded = product1;
        productAdded.setId(1);
        when(service.findProductById(1)).thenReturn(productAdded);

        MockHttpServletResponse deleteResponse = mockMvc.perform(
                delete("/admin/product/{id}", 1).with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(deleteResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        verify(service, times(1)).deleteProduct(product1.getId());
    }

    // ==================================================== //
    //                  INVOICE TESTS                       //
    // ==================================================== //
    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = {"ADMIN"})
    public void shouldRetrieveAllInvoices() throws Exception {
        Invoice invoice1 = new Invoice();
        Invoice invoice2 = new Invoice();
        invoice1.setId(1);
        invoice2.setId(2);
        List<Invoice> invoices = new ArrayList<Invoice>() {{
            add(invoice1);
            add(invoice2);
        }};
        given(service.findAllInvoices())
                .willReturn(invoices);
        MockHttpServletResponse allResponse = mockMvc.perform(
                get("/admin/invoices")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(allResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(allResponse.getContentAsString()).isEqualTo(jsonInvoiceList.write(invoices).getJson());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = {"ADMIN"})
    public void shouldRetrieveInvoiceById() throws Exception {
        Invoice invoice1 = new Invoice();
        invoice1.setId(1);
        given(service.findInvoiceById(1))
                .willReturn(invoice1);
        MockHttpServletResponse id1Response = mockMvc.perform(
                get("/admin/invoice/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(id1Response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(id1Response.getContentAsString()).isEqualTo(jsonInvoice.write(invoice1).getJson());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = {"ADMIN"})
    public void shouldCreateInvoices() throws Exception {
        Invoice invoice1 = new Invoice();
        Invoice invoiceAdded = invoice1;
        invoiceAdded.setId(1);
        given(service.createNewInvoice(invoice1))
                .willReturn(invoiceAdded);
        MockHttpServletResponse createdResponse = mockMvc.perform(
                post("/admin/invoice").with(csrf())
                        .content(jsonInvoice.write(invoice1).getJson())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(createdResponse.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(createdResponse.getContentAsString()).isEqualTo(jsonInvoice.write(invoiceAdded).getJson());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = {"ADMIN"})
    public void shouldUpdateInvoices() throws Exception {
        Invoice invoice1 = new Invoice();
        Invoice invoiceAdded = invoice1;
        invoiceAdded.setId(1);

        when(service.findInvoiceById(1)).thenReturn(invoiceAdded);

        Invoice invoice2 = new Invoice();
        invoice2.setId(1);
        MockHttpServletResponse updateResponse = mockMvc.perform(
                put("/admin/invoice").with(csrf())
                        .content(jsonInvoice.write(invoice2).getJson())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(updateResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        verify(service, times(1)).updateInvoice(invoice2);
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = {"ADMIN"})
    public void shouldDeleteInvoices() throws Exception {
        Invoice invoice1 = new Invoice();
        Invoice invoiceAdded = invoice1;
        invoiceAdded.setId(1);
        when(service.findInvoiceById(1)).thenReturn(invoiceAdded);

        MockHttpServletResponse deleteResponse = mockMvc.perform(
                delete("/admin/invoice/{id}", 1).with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(deleteResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        verify(service, times(1)).deleteInvoice(invoice1.getId());
    }




    @Test
    public void shouldForbidFromCreatingAndDeletingAndUpdatingProduct() throws Exception {
        Product product1 = new Product("Rock Em Sock Em Robots", "Punching Robits", new BigDecimal("15.99"), new BigDecimal("6.25"), 200);
        Product productUpdate = new Product("Rock Em Sock Em Robots", "Punching Robits", new BigDecimal("15.99"), new BigDecimal("6.25"), 200);
        Product productAdded = product1;
        productAdded.setId(1);
        productUpdate.setId(1);
        given(service.createNewProduct(product1))
                .willReturn(productAdded);

        MockHttpServletResponse createResponse = mockMvc.perform(
                post("/admin/product").with(csrf()).content(jsonProduct.write(product1).getJson())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(createResponse.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());

        MockHttpServletResponse updateResponse = mockMvc.perform(
                put("/admin/product/{id}", 1).with(csrf())
                        .content(jsonProduct.write(productUpdate).getJson())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(updateResponse.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());

        MockHttpServletResponse deleteResponse = mockMvc.perform(
                delete("/admin/product/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(deleteResponse.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void shouldForbidFromCreatingAndDeletingAndUpdatingCustomer() throws Exception {
        Customer customer1 = new Customer("Justin", "Case", "A5 Downtown ", "New Old Vegas", "F823-0021", "Image@Program.io", "10-888-992-99483");
        Customer customerUpdate = new Customer("Justin", "Case", "A5 Downtown ", "New Old Vegas", "F823-0021", "Image@Program.io", "10-888-992-99483");
        Customer customerAdded = customer1;
        customerAdded.setId(1);
        customerUpdate.setId(1);
        given(service.createNewCustomer(customer1))
                .willReturn(customerAdded);


        MockHttpServletResponse createResponse = mockMvc.perform(
                post("/admin/customer")
                        .content(jsonCustomer.write(customer1).getJson())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(createResponse.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());

        MockHttpServletResponse updateResponse = mockMvc.perform(
                put("/admin/customer/{id}", 1)
                        .content(jsonCustomer.write(customerUpdate).getJson())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(updateResponse.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());

        MockHttpServletResponse deleteResponse = mockMvc.perform(
                delete("/admin/customer/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(deleteResponse.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @WithMockUser
    public void shouldForbidFromCreatingAndUpdatingLevelUp() throws Exception {
        LevelUp levelup1 = new LevelUp(1,150, LocalDate.of(2019, 5,5));
        LevelUp levelupUpdate = new LevelUp(1,150, LocalDate.of(2019, 5,5));
        LevelUp levelupAdded = levelup1;
        levelupAdded.setId(1);
        levelupUpdate.setId(1);
        given(service.createNewLevelUp(levelup1))
                .willReturn(levelupAdded);

        MockHttpServletResponse forbCreateResponse = mockMvc.perform(
                post("/admin/levelup")
                        .content(jsonLevelUp.write(levelup1).getJson())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(forbCreateResponse.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());

        MockHttpServletResponse updateResponse = mockMvc.perform(
                put("/admin/levelUp")
                        .content(jsonLevelUp.write(levelupUpdate).getJson())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(updateResponse.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void shouldForbidFromCreatingAndDeletingAndUpdatingInvoice() throws Exception {
        Invoice invoice1 = new Invoice(1,LocalDate.of(2019, 5,5));
        Invoice invoiceUpdate = new Invoice(1,LocalDate.of(2019, 5,5));
        Invoice invoiceAdded = invoice1;
        invoiceAdded.setId(1);
        invoiceUpdate.setId(1);

        given(service.createNewInvoice(invoice1))
                .willReturn(invoiceAdded);

        MockHttpServletResponse createResponse = mockMvc.perform(
                post("/admin/invoice").with(csrf()).content(jsonInvoice.write(invoice1).getJson())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(createResponse.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());

        MockHttpServletResponse updateResponse = mockMvc.perform(
                put("/admin/invoice/{id}", 1).with(csrf()).content(jsonInvoice.write(invoiceUpdate).getJson())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(updateResponse.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());

        MockHttpServletResponse deleteResponse = mockMvc.perform(
                delete("/admin/invoice/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(deleteResponse.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }
}