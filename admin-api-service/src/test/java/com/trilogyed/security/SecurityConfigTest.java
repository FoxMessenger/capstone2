//package com.trilogyed.security;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.json.JacksonTester;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import javax.sql.DataSource;
//
//import static org.junit.Assert.*;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//
//// Resource
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(SecurityConfig.class)
//public class  SecurityConfigTest{
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private Cart cart;
//
//    @MockBean
//    DataSource dataSource; //Need a mock bean of the data source
//
//    private JacksonTester<Invoice> jsonInvoice;
//    private JacksonTester<List<Invoice>> jsonInvoiceList;
//
//    @Before
//    public void setUp() throws Exception {
//        JacksonTester.initFields(this, new ObjectMapper());
//    }
//
//    @Test
//    @WithMockUser //Need to mock a user in order to test methods. When given no params it just mocks a default "user" with no "role" or "authority"
//    public void shouldRetrieveAllInvoicesAndRetrieveById() throws Exception {
//        Invoice invoice1 = new Invoice("15.25", 25, "XL", "Red", "Doom");
//        Invoice invoice2 = new Invoice("25.00", 100, "S", "Green", "Minecraft");
//        ivnoice1.setId(1);
//        invoice2.setId(2);
//        List<TShirt> tShirts = new ArrayList<TShirt>(){{add(tShirt1); add(tShirt2);}};
//
//        given(cart.getAllInvoices())
//                .willReturn(invoices);
//
//        MockHttpServletResponse getAllResponse = mockMvc.perform(
//                get("/invoices").accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        assertThat(getAllResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(getAllResponse.getContentAsString()).isEqualTo(jsonTSList.write(tShirts).getJson());
//
//        given(cart.getTShirtById(1))
//                .willReturn(tShirt1);
//
//        MockHttpServletResponse getID1Response = mockMvc.perform(
//                get("/tshirt/{id}", 1).with(csrf())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        assertThat(getID1Response.getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(getID1Response.getContentAsString()).isEqualTo(jsonTS.write(tShirt1).getJson());
//    }
//
//    @Test
//    @WithMockUser
//    public void shouldRetrieveTShirtsByColorAndSize() throws Exception {
//        TShirt tShirt1 = new TShirt("15.25", 25, "XL", "Red", "Doom");
//        tShirt1.setId(1);
//        TShirt tShirt2 = new TShirt("25.00", 100, "S", "Green", "Minecraft");
//        tShirt2.setId(2);
//        TShirt tShirt3 = new TShirt("16.99", 50, "L", "Blue", "Fez");
//        tShirt3.setId(3);
//        TShirt tShirt4 = new TShirt("15.25", 75, "S", "Red", "Sims");
//        tShirt4.setId(4);
//        TShirt tShirt5 = new TShirt("15.25", 25, "XL", "Red", "Doom 3");
//        tShirt5.setId(5);
//        TShirt tShirt6 = new TShirt("25.00", 100, "S", "Green", "Roblox");
//        tShirt6.setId(6);
//        List<TShirt> red = new ArrayList<TShirt>() {{add(tShirt1); add(tShirt4); add(tShirt5);}};
//        List<TShirt> green = new ArrayList<TShirt>() {{add(tShirt2); add(tShirt6);}};
//        List<TShirt> small = new ArrayList<TShirt>() {{add(tShirt2); add(tShirt4); add(tShirt6);}};
//        List<TShirt> xL = new ArrayList<TShirt>() {{add(tShirt1); add(tShirt5);}};
//
//        given(cart.getTShirtsByColor("Red"))
//                .willReturn(red);
//
//        MockHttpServletResponse redResponse = mockMvc.perform(
//                get("/tshirt/searchByColor?color=Red")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        assertThat(redResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(redResponse.getContentAsString()).isEqualTo(jsonTSList.write(red).getJson());
//
//        given(cart.getTShirtsByColor("Green"))
//                .willReturn(green);
//
//        MockHttpServletResponse greenResponse = mockMvc.perform(
//                get("/tshirt/searchByColor?color=Green")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        assertThat(greenResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(greenResponse.getContentAsString()).isEqualTo(jsonTSList.write(green).getJson());
//
//        given(cart.getTShirtsBySize("S"))
//                .willReturn(small);
//
//        MockHttpServletResponse smallResponse = mockMvc.perform(
//                get("/tshirt/searchBySize?size=S")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        assertThat(smallResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(smallResponse.getContentAsString()).isEqualTo(jsonTSList.write(small).getJson());
//
//        given(cart.getTShirtsBySize("XL"))
//                .willReturn(xL);
//
//        MockHttpServletResponse xLResponse = mockMvc.perform(
//                get("/tshirt/searchBySize?size=XL")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        assertThat(xLResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(xLResponse.getContentAsString()).isEqualTo(jsonTSList.write(xL).getJson());
//    }
//
//    @Test //Test that you get a 401 error when you don't mock a user with @WithMockUser and you attempt to access the post, put, or delete methods with the correct X-XSRF-TOKEN
//    public void shouldThrow401WhenNoUserInfoGivenAndAttemptingToPostPutOrDelete() throws Exception {
//        TShirt tShirt1 = new TShirt("15.25", 25, "XL", "Red", "Doom");
//        TShirt tShirtUpdate = new TShirt("25.00", 100, "S", "Green", "Minecraft");
//        TShirt tShirtAdded = tShirt1;
//        tShirtAdded.setId(1);
//        tShirtUpdate.setId(1);
//
//        given(cart.addTShirt(tShirt1))
//                .willReturn(tShirtAdded);
//
//        MockHttpServletResponse createResponse = mockMvc.perform(
//                post("/tshirt").with(csrf()).content(jsonTS.write(tShirt1).getJson())
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        assertThat(createResponse.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
//
//        MockHttpServletResponse updateResponse = mockMvc.perform(
//                put("/update/tshirt").with(csrf()).content(jsonTS.write(tShirtUpdate).getJson())
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        assertThat(updateResponse.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
//
//        MockHttpServletResponse deleteResponse = mockMvc.perform(
//                delete("/tshirt/del/{id}", 1).with(csrf())//.with(csrf()) method sets the X-XSRF-TOKEN
//                        .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        assertThat(deleteResponse.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
//    }
//
//    @Test
//    @WithMockUser(username = "JDoe", password = "jtho123", authorities = {"STAFF"}) //Mocks my user with given username, password, and authority. Test controller as if I logged in as this user.
//    public void shouldUpdateAConsoleAndForbidCreatingAndDeletingAConsole() throws Exception {
//        TShirt tShirt1 = new TShirt("15.25", 25, "XL", "Red", "Doom");
//        TShirt tShirtUpdate = new TShirt("25.00", 100, "S", "Green", "Minecraft");
//        TShirt tShirtAdded = tShirt1;
//        tShirtAdded.setId(1);
//        tShirtUpdate.setId(1);
//
//        given(cart.addTShirt(tShirt1))
//                .willReturn(tShirtAdded);
//
//        MockHttpServletResponse createResponse = mockMvc.perform(
//                post("/tshirt").with(csrf()).content(jsonTS.write(tShirt1).getJson())
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        assertThat(createResponse.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value()); //STAFF even when authenticated can't create an item
//
//        MockHttpServletResponse updateResponse = mockMvc.perform(
//                put("/update/tshirt").with(csrf()).content(jsonTS.write(tShirtUpdate).getJson())
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        assertThat(updateResponse.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
//
//        MockHttpServletResponse deleteResponse = mockMvc.perform(
//                delete("/tshirt/del/{id}", 1).with(csrf())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        assertThat(deleteResponse.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());//STAFF even when authenticated can't delete an item
//    }
//
//    @Test
//    @WithMockUser(username = "WreckIt", password = "ralphie", authorities = {"MANAGER"})
//    public void shouldCreateAndUpdateAConsoleAndForbidDeletingAConsole() throws Exception {
//        TShirt tShirt1 = new TShirt("15.25", 25, "XL", "Red", "Doom");
//        TShirt tShirtUpdate = new TShirt("25.00", 100, "S", "Green", "Minecraft");
//        TShirt tShirtAdded = tShirt1;
//        tShirtAdded.setId(1);
//        tShirtUpdate.setId(1);
//
//        given(cart.addTShirt(tShirt1))
//                .willReturn(tShirtAdded);
//
//        MockHttpServletResponse createResponse = mockMvc.perform(
//                post("/tshirt").with(csrf()).content(jsonTS.write(tShirt1).getJson())
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        assertThat(createResponse.getStatus()).isEqualTo(HttpStatus.CREATED.value());
//        assertThat(createResponse.getContentAsString()).isEqualTo(jsonTS.write(tShirtAdded).getJson());
//
//        MockHttpServletResponse updateResponse = mockMvc.perform(
//                put("/update/tshirt").with(csrf()).content(jsonTS.write(tShirtUpdate).getJson())
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        assertThat(updateResponse.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
//
//        MockHttpServletResponse deleteResponse = mockMvc.perform(
//                delete("/tshirt/del/{id}", 1).with(csrf())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        assertThat(deleteResponse.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
//    }
//
//    @Test
//    @WithMockUser(username = "TheBoss", password = "BossMan", authorities = {"ADMIN"})
//    public void shouldCreateUpdateAndDeleteTShirt() throws Exception {
//        TShirt tShirt1 = new TShirt("15.25", 25, "XL", "Red", "Doom");
//        TShirt tShirtUpdate = new TShirt("25.00", 100, "S", "Green", "Minecraft");
//        TShirt tShirtAdded = tShirt1;
//        tShirtAdded.setId(1);
//        tShirtUpdate.setId(1);
//
//        given(cart.addTShirt(tShirt1))
//                .willReturn(tShirtAdded);
//
//        MockHttpServletResponse createResponse = mockMvc.perform(
//                post("/tshirt").with(csrf()).content(jsonTS.write(tShirt1).getJson())
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        assertThat(createResponse.getStatus()).isEqualTo(HttpStatus.CREATED.value());
//        assertThat(createResponse.getContentAsString()).isEqualTo(jsonTS.write(tShirtAdded).getJson());
//
//        MockHttpServletResponse updateResponse = mockMvc.perform(
//                put("/update/tshirt").with(csrf()).content(jsonTS.write(tShirtUpdate).getJson())
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        assertThat(updateResponse.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
//
//        MockHttpServletResponse deleteResponse = mockMvc.perform(
//                delete("/tshirt/del/{id}", 1).with(csrf())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        assertThat(deleteResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
//    }
//}