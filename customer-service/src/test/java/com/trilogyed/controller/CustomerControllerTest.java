package com.trilogyed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.model.Customer;
import com.trilogyed.repository.CustomerRepo;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerRepo repo;
    private JacksonTester<Customer> jsonCustomer;
    private JacksonTester<List<Customer>> jsonCustomerList;

    @Before
    public void setUp() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void shouldRetrieveAllCustomers() throws Exception {
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        customer1.setId(1);
        customer2.setId(2);
        List<Customer> customers = new ArrayList<Customer>() {{
            add(customer1);
            add(customer2);
        }};
        given(repo.findAll())
                .willReturn(customers);
        MockHttpServletResponse allResponse = mockMvc.perform(
                get("/customers")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(allResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(allResponse.getContentAsString()).isEqualTo(jsonCustomerList.write(customers).getJson());
    }

    @Test
    public void shouldRetrieveCustomerById() throws Exception {
        Customer customer1 = new Customer();
        customer1.setId(1);
        given(repo.findById(1))
                .willReturn(Optional.of(customer1));

        given(repo.getOne(1))
                .willReturn(customer1);
        MockHttpServletResponse id1Response = mockMvc.perform(
                get("/customer/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(id1Response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(id1Response.getContentAsString()).isEqualTo(jsonCustomer.write(customer1).getJson());
    }

    @Test
    public void shouldCreateCustomers() throws Exception {
        Customer customer1 = new Customer();
        Customer customerAdded = customer1;
        customerAdded.setId(1);
        given(repo.save(customer1))
                .willReturn(customerAdded);
        MockHttpServletResponse createdResponse = mockMvc.perform(
                post("/customer")
                        .content(jsonCustomer.write(customer1).getJson())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(createdResponse.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(createdResponse.getContentAsString()).isEqualTo(jsonCustomer.write(customerAdded).getJson());
    }

    @Test
    public void shouldUpdateCustomers() throws Exception {
        Customer customer1 = new Customer();
        Customer customerAdded = customer1;
        customerAdded.setId(1);
        given(repo.save(customer1))
                .willReturn(customerAdded);
        Customer customer2 = new Customer();
        customer2.setId(1);
        MockHttpServletResponse updateResponse = mockMvc.perform(
                put("/customer")
                        .content(jsonCustomer.write(customer2).getJson())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(updateResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        verify(repo, times(1)).save(customer2);
    }

    @Test
    public void shouldDeleteCustomers() throws Exception {
        Customer customer1 = new Customer();
        Customer customerAdded = customer1;
        customerAdded.setId(1);
        given(repo.save(customer1))
                .willReturn(customerAdded);
        MockHttpServletResponse deleteResponse = mockMvc.perform(
                delete("/customer/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        
        assertThat(deleteResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        verify(repo, times(1)).deleteById(customer1.getId());
    }
}