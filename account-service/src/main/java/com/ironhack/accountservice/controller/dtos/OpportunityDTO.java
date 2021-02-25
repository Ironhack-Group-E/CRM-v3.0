package com.ironhack.accountservice.controller.dtos;

import com.ironhack.accountservice.enums.Product;
import com.ironhack.accountservice.enums.Status;
import com.ironhack.accountservice.model.Account;
import com.ironhack.accountservice.model.Contact;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OpportunityDTO {

    private Integer id;
    private Product product;
    @Min(0)
    private int quantity;
    private ContactDTO decisionMaker;
    private Status status;
    @Min(1)
    private Integer salesRepId;
    private AccountDTO account;

    public OpportunityDTO() {
    }

    public OpportunityDTO(Integer id, Product product, int quantity, ContactDTO decisionMaker, Status status, Integer salesRepId, AccountDTO account) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.decisionMaker = decisionMaker;
        this.status = status;
        this.salesRepId = salesRepId;
        this.account = account;
    }

    public OpportunityDTO(Product product, int quantity, ContactDTO decisionMaker, Status status, Integer salesRepId, AccountDTO account) {
        this.product = product;
        this.quantity = quantity;
        this.decisionMaker = decisionMaker;
        this.status = status;
        this.salesRepId = salesRepId;
        this.account = account;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ContactDTO getDecisionMaker() {
        return decisionMaker;
    }

    public void setDecisionMaker(ContactDTO decisionMaker) {
        this.decisionMaker = decisionMaker;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getSalesRepId() {
        return salesRepId;
    }

    public void setSalesRepId(Integer salesRepId) {
        this.salesRepId = salesRepId;
    }

    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }
}
