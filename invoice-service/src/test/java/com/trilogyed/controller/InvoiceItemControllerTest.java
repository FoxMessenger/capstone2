package com.trilogyed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.model.InvoiceItem;
import com.trilogyed.repository.InvoiceItemRepo;
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
@WebMvcTest(InvoiceItemController.class)
public class InvoiceItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceItemRepo itemRepo;

    private JacksonTester<InvoiceItem> jsonInvoiceItem;

    private JacksonTester<List<InvoiceItem>> jsonInvoiceItemList;

    @Before
    public void setUp() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void shouldRetrieveAllInvoiceItems() throws Exception {
        InvoiceItem invoiceItem1 = new InvoiceItem();
        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem1.setId(1);
        invoiceItem2.setId(2);
        List<InvoiceItem> invoiceItems = new ArrayList<InvoiceItem>() {{
            add(invoiceItem1);
            add(invoiceItem2);
        }};

        given(itemRepo.findAll())
                .willReturn(invoiceItems);

        MockHttpServletResponse allResponse = mockMvc.perform(
                get("/invoiceItems")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(allResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(allResponse.getContentAsString()).isEqualTo(jsonInvoiceItemList.write(invoiceItems).getJson());
    }

    @Test
    public void shouldRetrieveInvoiceItemsByInvoiceId() throws Exception {
        InvoiceItem invoiceItem1 = new InvoiceItem();
        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem1.setId(1);
        invoiceItem1.setInvoiceId(1);
        invoiceItem2.setId(2);
        invoiceItem2.setInvoiceId(1);

        List<InvoiceItem> invoiceItems = new ArrayList<InvoiceItem>() {{
            add(invoiceItem1);
            add(invoiceItem2);
        }};

        given(itemRepo.findByInvoiceId(1))
                .willReturn(invoiceItems);

        MockHttpServletResponse invoiceResponse = mockMvc.perform(
                get("/invoiceItems/invoice?id=1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(invoiceResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(invoiceResponse.getContentAsString()).isEqualTo(jsonInvoiceItemList.write(invoiceItems).getJson());
    }

    @Test
    public void shouldRetrieveInvoiceItemsByProductId() throws Exception {
        InvoiceItem invoiceItem1 = new InvoiceItem();
        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem1.setId(1);
        invoiceItem1.setProductId(1);
        invoiceItem2.setId(2);
        invoiceItem2.setProductId(1);

        List<InvoiceItem> invoiceItems = new ArrayList<InvoiceItem>() {{
            add(invoiceItem1);
            add(invoiceItem2);
        }};

        given(itemRepo.findByProductId(1))
                .willReturn(invoiceItems);

        MockHttpServletResponse productResponse = mockMvc.perform(
                get("/invoiceItems/product?id=1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(productResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(productResponse.getContentAsString()).isEqualTo(jsonInvoiceItemList.write(invoiceItems).getJson());
    }

    @Test
    public void shouldRetrieveInvoiceItemById() throws Exception {
        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setId(1);

        given(itemRepo.findById(1))
                .willReturn(Optional.of(invoiceItem1));

        given(itemRepo.getOne(1))
                .willReturn(invoiceItem1);

        MockHttpServletResponse id1Response = mockMvc.perform(
                get("/invoiceItem/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(id1Response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(id1Response.getContentAsString()).isEqualTo(jsonInvoiceItem.write(invoiceItem1).getJson());
    }

    @Test
    public void shouldCreateInvoiceItems() throws Exception {
        InvoiceItem invoiceItem1 = new InvoiceItem();
        InvoiceItem invoiceItemAdded = invoiceItem1;
        invoiceItemAdded.setId(1);

        given(itemRepo.save(invoiceItem1))
                .willReturn(invoiceItemAdded);

        MockHttpServletResponse createdResponse = mockMvc.perform(
                post("/invoiceItem")
                        .content(jsonInvoiceItem.write(invoiceItem1).getJson())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(createdResponse.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(createdResponse.getContentAsString()).isEqualTo(jsonInvoiceItem.write(invoiceItemAdded).getJson());
    }

    @Test
    public void shouldUpdateInvoiceItems() throws Exception {
        InvoiceItem invoiceItem1 = new InvoiceItem();
        InvoiceItem invoiceItemAdded = invoiceItem1;
        invoiceItemAdded.setId(1);

        given(itemRepo.save(invoiceItem1))
                .willReturn(invoiceItemAdded);

        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setId(1);

        MockHttpServletResponse updateResponse = mockMvc.perform(
                put("/invoiceItem")
                        .content(jsonInvoiceItem.write(invoiceItem2).getJson())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(updateResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        verify(itemRepo, times(1)).save(invoiceItem2);
    }

    @Test
    public void shouldDeleteInvoiceItems() throws Exception {
        InvoiceItem invoiceItem1 = new InvoiceItem();
        InvoiceItem invoiceItemAdded = invoiceItem1;
        invoiceItemAdded.setId(1);

        given(itemRepo.save(invoiceItem1))
                .willReturn(invoiceItemAdded);

        MockHttpServletResponse deleteResponse = mockMvc.perform(
                delete("/invoiceItem/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(deleteResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        verify(itemRepo, times(1)).deleteById(invoiceItem1.getId());
    }
}