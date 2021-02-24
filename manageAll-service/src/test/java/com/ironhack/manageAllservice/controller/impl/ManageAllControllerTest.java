package com.ironhack.manageAllservice.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.manageAllservice.controller.dtos.LeadDTO;
import com.ironhack.manageAllservice.controller.dtos.SalesRepDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ManageAllControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {

        //mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void newSalesRep_ValidSalesRepDTO_SalesRepDTO() throws Exception {
        SalesRepDTO salesRepDTO = new SalesRepDTO("Antonio Martín");
        String body = objectMapper.writeValueAsString(salesRepDTO);

        MvcResult result = mockMvc.perform(post("/new-salesrep").content(body)
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
                .andExpect(status().isCreated())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString(Charset.defaultCharset()).contains("Antonio Martín"));
    }

    @Test
    public void newSalesRep_NotValidSalesRepDTO_BadRequest() throws Exception {
        SalesRepDTO salesRepDTO = new SalesRepDTO("56416516");
        String body = objectMapper.writeValueAsString(salesRepDTO);

        MvcResult result = mockMvc.perform(post("/new-salesrep").content(body)
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void showSalesRep() throws Exception {
        MvcResult result = mockMvc.perform(get("/salesrep")
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(!result.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void lookUpSalesRep_ValidId_SalesRep() throws Exception {
        MvcResult result = mockMvc.perform(get("/salesrep/6")
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString(Charset.defaultCharset()).contains("Antonio"));
    }

    @Test
    public void lookUpSalesRep_NotValidId_ServiceUnavailable() throws Exception {
        MvcResult result = mockMvc.perform(get("/salesrep/10000")
                .characterEncoding("UTF-8"))
                .andExpect(status().isServiceUnavailable())
                .andReturn();
    }

    @Test
    public void newLead_ValidLeadDTO_LeadDTO() throws Exception {
        LeadDTO leadDTO = new LeadDTO("María López", "marial@gmail.com", "IKEA", "666333222", 1);
        String body = objectMapper.writeValueAsString(leadDTO);

        MvcResult result = mockMvc.perform(post("/new-lead").content(body)
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
                .andExpect(status().isCreated())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString(Charset.defaultCharset()).contains("María López"));
    }

    @Test
    public void newLead_NotValidLeadDTO_BadRequest() throws Exception {
        LeadDTO leadDTO = new LeadDTO("4256324", "45632465", "5432453", "hgrestyhrth", 1);
        String body = objectMapper.writeValueAsString(leadDTO);

        MvcResult result = mockMvc.perform(post("/new-lead").content(body)
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void showLeads() throws Exception {
        MvcResult result = mockMvc.perform(get("/lead")
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(!result.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void lookUpLead_ValidId_Lead() throws Exception {
        MvcResult result = mockMvc.perform(get("/lead/12")
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString(Charset.defaultCharset()).contains("María López"));
    }

    @Test
    public void lookUpLead_NotValidId_ServiceUnavailable() throws Exception {
        MvcResult result = mockMvc.perform(get("/lead/10000")
                .characterEncoding("UTF-8"))
                .andExpect(status().isServiceUnavailable())
                .andReturn();
    }
}