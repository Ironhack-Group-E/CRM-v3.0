package com.ironhack.accountservice.model;

import com.ironhack.accountservice.enums.Product;
import com.ironhack.accountservice.enums.Status;

import javax.persistence.*;

@Entity
public class Opportunity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(value = EnumType.STRING)
    private Product product;
    private int quantity;
    @OneToOne
    private Contact decisionMaker;
    @Enumerated(value = EnumType.STRING)
    private Status status;
    private Integer salesRepId;
    @ManyToOne
    private Account account;

    public Opportunity() {
    }

    public Opportunity(Integer id, Product product, int quantity, Contact decisionMaker, Status status) {
        setId(id);
        setProduct(product);
        setQuantity(quantity);
        setDecisionMaker(decisionMaker);
        setStatus(status);
    }


    //Constructor for a new Opportunity
    public Opportunity(Product product, int quantity, Contact decisionMaker, Status status, Account account, Integer salesRepId) {
        setProduct(product);
        setQuantity(quantity);
        setDecisionMaker(decisionMaker);
        setStatus(status);
        setAccount(account);
        setSalesRep(salesRepId);
    }

    public Opportunity(Integer id, Product product, int quantity, Contact decisionMaker, Status status, Account account, Integer salesRepId) {
        setId(id);
        setProduct(product);
        setQuantity(quantity);
        setDecisionMaker(decisionMaker);
        setStatus(status);
        setAccount(account);
        setSalesRep(salesRepId);
    }

    //Constructor for a new Opportunity
    public Opportunity(Product product, int quantity, Contact decisionMaker, Status status) {
        setProduct(product);
        setQuantity(quantity);
        setDecisionMaker(decisionMaker);
        setStatus(status);
    }

    //Getters and setters
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
        if (quantity <= 0) {
            throw new IllegalArgumentException("The number of trucks must be greater than zero.");
        }
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

    public Integer getSalesRep() {
        return salesRepId;
    }

    public void setSalesRep(Integer salesRepId) {
        this.salesRepId = salesRepId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
