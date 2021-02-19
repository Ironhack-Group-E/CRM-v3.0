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
    @NotNull
    private Contact decisionMaker;
    private Status status;
    @Min(1)
    private Integer salesRepId;
    @NotNull
    private Account account;

    public OpportunityDTO() {
    }

    public OpportunityDTO(Integer id, Product product, int quantity, Contact decisionMaker, Status status, Integer salesRepId, Account account) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.decisionMaker = decisionMaker;
        this.status = status;
        this.salesRepId = salesRepId;
        this.account = account;
    }

    public OpportunityDTO(Product product, int quantity, Contact decisionMaker, Status status, Integer salesRepId, Account account) {
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

    public Contact getDecisionMaker() {
        return decisionMaker;
    }

    public void setDecisionMaker(Contact decisionMaker) {
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
