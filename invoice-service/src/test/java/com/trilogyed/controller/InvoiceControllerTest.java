package com.trilogyed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.model.Invoice;
import com.trilogyed.repository.InvoiceRepo;
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
@WebMvcTest(InvoiceController.class)
public class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceRepo invoiceRepo;

    private JacksonTester<Invoice> jsonInvoice;

    private JacksonTester<List<Invoice>> jsonInvoiceList;

    @Before
    public void setUp() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
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

        given(invoiceRepo.findAll())
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

        given(invoiceRepo.findById(1))
                .willReturn(Optional.of(invoice1));

        given(invoiceRepo.getOne(1))
                .willReturn(invoice1);

        MockHttpServletResponse id1Response = mockMvc.perform(
                get("/invoice/{id}", 1)
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

        given(invoiceRepo.findByCustomerId(1))
                .willReturn(invoices);

        MockHttpServletResponse id1Response = mockMvc.perform(
                get("/invoice/customer/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(id1Response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(id1Response.getContentAsString()).isEqualTo(jsonInvoiceList.write(invoices).getJson());
    }

    @Test
    public void shouldCreateInvoices() throws Exception {
        Invoice invoice1 = new Invoice();
        Invoice invoiceAdded = invoice1;
        invoiceAdded.setId(1);

        given(invoiceRepo.save(invoice1))
                .willReturn(invoiceAdded);

        MockHttpServletResponse createdResponse = mockMvc.perform(
                post("/invoice")
                        .content(jsonInvoice.write(invoice1).getJson())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(createdResponse.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(createdResponse.getContentAsString()).isEqualTo(jsonInvoice.write(invoiceAdded).getJson());
    }

    @Test
    public void shouldUpdateInvoices() throws Exception {
        Invoice invoice1 = new Invoice();
        Invoice invoiceAdded = invoice1;
        invoiceAdded.setId(1);

        given(invoiceRepo.save(invoice1))
                .willReturn(invoiceAdded);

        Invoice invoice2 = new Invoice();
        invoice2.setId(1);

        MockHttpServletResponse updateResponse = mockMvc.perform(
                put("/invoice")
                        .content(jsonInvoice.write(invoice2).getJson())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(updateResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        verify(invoiceRepo, times(1)).save(invoice2);
    }

    @Test
    public void shouldDeleteInvoices() throws Exception {
        Invoice invoice1 = new Invoice();
        Invoice invoiceAdded = invoice1;
        invoiceAdded.setId(1);

        given(invoiceRepo.save(invoice1))
                .willReturn(invoiceAdded);

        MockHttpServletResponse deleteResponse = mockMvc.perform(
                delete("/invoice/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(deleteResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        verify(invoiceRepo, times(1)).deleteById(invoice1.getId());
    }
}