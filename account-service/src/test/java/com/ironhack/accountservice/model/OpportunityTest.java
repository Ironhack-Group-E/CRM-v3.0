package com.ironhack.accountservice.model;

import com.ironhack.accountservice.enums.Product;
import com.ironhack.accountservice.enums.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpportunityTest {

    private Contact contact;

    @BeforeEach
    public void beforeAll() {
        contact = new Contact("Ivan", "ivan@gmail.com", "Company", "922 33 22 11");
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    public void setQuantity_AboveZero_OpportunityCreated() {
        Opportunity opportunity = new Opportunity(Product.HYBRID, 5, contact, Status.OPEN);

        assertEquals(5, opportunity.getQuantity());
    }

    @Test
    public void setQuantity_ZeroOrNegative_IllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Opportunity(Product.HYBRID, -5, contact, Status.OPEN));
        assertThrows(IllegalArgumentException.class, () -> new Opportunity(Product.HYBRID, 0, contact, Status.OPEN));
    }

}