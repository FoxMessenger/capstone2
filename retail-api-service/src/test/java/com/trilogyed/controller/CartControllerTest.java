package com.trilogyed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.DbFiller;
import com.trilogyed.model.Invoice;
import com.trilogyed.model.LevelUp;
import com.trilogyed.model.Order;
import com.trilogyed.model.Product;
import com.trilogyed.service.Cart;
import com.trilogyed.viewmodel.InvoiceViewModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CartController.class)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DbFiller dbFiller;

    @MockBean
    private Cart cart;

    private JacksonTester<Order> jsonOrder;

    private JacksonTester<InvoiceViewModel> jsonInvoiceViewModel;

    private JacksonTester<Invoice> jsonInvoice;

    private JacksonTester<List<Invoice>> jsonInvoiceList;

    private JacksonTester<List<InvoiceViewModel>> jsonInvoiceViewModelList;

    private JacksonTester<Product> jsonProduct;

    private JacksonTester<List<Product>> jsonProductList;

    private JacksonTester<LevelUp> jsonLevelUp;

    private JacksonTester<Integer> jsonLevelUpPoints;

    @Before
    public void setUp() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void shouldCreateInvoiceViewModels() throws Exception {
        Order order = new Order();
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();

        given(cart.makeAnOrder(order))
                .willReturn(invoiceViewModel);

        MockHttpServletResponse createdResponse = mockMvc.perform(
                post("/order")
                        .content(jsonOrder.write(order).getJson())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(createdResponse.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(createdResponse.getContentAsString()).isEqualTo(jsonInvoiceViewModel.write(invoiceViewModel).getJson());
    }

    @Test
    public void shouldRetrieveAllInvoices() throws Exception {
        Invoice invoice1 = new Invoice();
        Invoice invoice2 = new Invoice();
        invoice1.setId(1);
        invoice2.setId(2);
        List<Invoice> invoices = new ArrayList<Invoice>() {{
            add(invoice1);
            add(invoice2);
        }};

        given(cart.findAllInvoices())
                .willReturn(invoices);

        MockHttpServletResponse allResponse = mockMvc.perform(
                get("/invoices")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(allResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(allResponse.getContentAsString()).isEqualTo(jsonInvoiceList.write(invoices).getJson());
    }

    @Test
    public void shouldRetrieveInvoiceById() throws Exception {
        Invoice invoice1 = new Invoice();
        invoice1.setId(1);

        given(cart.findInvoiceById(1))
                .willReturn(invoice1);

        MockHttpServletResponse id1Response = mockMvc.perform(
                get("/invoices/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(id1Response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(id1Response.getContentAsString()).isEqualTo(jsonInvoice.write(invoice1).getJson());
    }

    @Test
    public void shouldRetrieveInvoicesByCustomerId() throws Exception {
        Invoice invoice1 = new Invoice();
        Invoice invoice2 = new Invoice();
        invoice1.setId(1);
        invoice1.setCustomerId(1);

        invoice2.setId(1);
        invoice2.setCustomerId(1);
        List<Invoice> invoices = new ArrayList<Invoice>(){{add(invoice1); add(invoice2);}};

        given(cart.findInvoiceByCustomerId(1))
                .willReturn(invoices);

        MockHttpServletResponse id1Response = mockMvc.perform(
                get("/invoices/customer/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(id1Response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(id1Response.getContentAsString()).isEqualTo(jsonInvoiceList.write(invoices).getJson());
    }

    @Test
    public void shouldRetrieveProductsWithInventory() throws Exception {
        Product product1 = new Product();
        Product product2 = new Product();
        product1.setId(1);
        product2.setId(2);
        List<Product> products = new ArrayList<Product>() {{
            add(product1);
            add(product2);
        }};
        given(cart.getProductsWithInventory())
                .willReturn(products);
        MockHttpServletResponse allResponse = mockMvc.perform(
                get("/products/inventory")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(allResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(allResponse.getContentAsString()).isEqualTo(jsonProductList.write(products).getJson());
    }

    @Test
    public void RetrieveProductsByInvoiceId() throws Exception {
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();

        List<Product> products = new ArrayList<Product>(){{add(product1); add(product2); add(product3);}};


        given(cart.getProductsByInvoiceId(1))
                .willReturn(products);

        MockHttpServletResponse productInvoiceResponse = mockMvc.perform(
                get("/products/invoice/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(productInvoiceResponse.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    public void shouldRetrieveProductById() throws Exception {
        Product product1 = new Product();
        product1.setId(1);

        given(cart.findProductById(1))
                .willReturn(product1);
        MockHttpServletResponse id1Response = mockMvc.perform(
                get("/products/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(id1Response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(id1Response.getContentAsString()).isEqualTo(jsonProduct.write(product1).getJson());
    }

    @Test
    public void shouldRetrieveLevelUpByCustomerId() throws Exception {
        LevelUp levelUp1 = new LevelUp();
        levelUp1.setId(1);

        given(cart.findLevelUpByCustomerId(1))
                .willReturn(levelUp1);

        MockHttpServletResponse id1Response = mockMvc.perform(
                get("/customer/points/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(id1Response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(id1Response.getContentAsString()).isEqualTo(jsonLevelUpPoints.write(150).getJson());
    }
}