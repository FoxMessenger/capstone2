package com.trilogyed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.model.Product;
import com.trilogyed.repository.ProductRepo;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductRepo repo;
    private JacksonTester<Product> jsonProduct;
    private JacksonTester<List<Product>> jsonProductList;

    @Before
    public void setUp() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void shouldRetrieveAllProducts() throws Exception {
        Product product1 = new Product();
        Product product2 = new Product();
        product1.setId(1);
        product2.setId(2);
        List<Product> products = new ArrayList<Product>() {{
            add(product1);
            add(product2);
        }};
        given(repo.findAll())
                .willReturn(products);
        MockHttpServletResponse allResponse = mockMvc.perform(
                get("/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(allResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(allResponse.getContentAsString()).isEqualTo(jsonProductList.write(products).getJson());
    }

    @Test
    public void shouldRetrieveProductById() throws Exception {
        Product product1 = new Product();
        product1.setId(1);
        given(repo.getOne(1))
                .willReturn(product1);
        MockHttpServletResponse id1Response = mockMvc.perform(
                get("/product/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(id1Response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(id1Response.getContentAsString()).isEqualTo(jsonProduct.write(product1).getJson());
    }

    @Test
    public void shouldCreateProducts() throws Exception {
        Product product1 = new Product();
        Product productAdded = product1;
        productAdded.setId(1);
        given(repo.save(product1))
                .willReturn(productAdded);
        MockHttpServletResponse createdResponse = mockMvc.perform(
                post("/product")
                        .content(jsonProduct.write(product1).getJson())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(createdResponse.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(createdResponse.getContentAsString()).isEqualTo(jsonProduct.write(productAdded).getJson());
    }

    @Test
    public void shouldUpdateProducts() throws Exception {
        Product product1 = new Product();
        Product productAdded = product1;
        productAdded.setId(1);
        given(repo.save(product1))
                .willReturn(productAdded);
        Product product2 = new Product();
        product2.setId(1);
        MockHttpServletResponse updateResponse = mockMvc.perform(
                put("/product")
                        .content(jsonProduct.write(product2).getJson())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(updateResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        verify(repo, times(1)).save(product2);
    }

    @Test
    public void shouldDeleteProducts() throws Exception {
        Product product1 = new Product();
        Product productAdded = product1;
        productAdded.setId(1);
        given(repo.save(product1))
                .willReturn(productAdded);
        MockHttpServletResponse deleteResponse = mockMvc.perform(
                delete("/product/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(deleteResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        verify(repo, times(1)).deleteById(product1.getId());
    }
}