package com.ironhack.accountservice.model;

import javax.persistence.*;

@Entity
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String companyName;
    private String phoneNumber;
    @ManyToOne
    private @javax.validation.constraints.NotNull Account account;

    @OneToOne(mappedBy = "decisionMaker")
    private Opportunity opportunity;

    public Contact() {
    }

    public Contact(Integer id, String name, String email, String companyName, String phoneNumber, @javax.validation.constraints.NotNull Account account, Opportunity opportunity) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
        this.account = account;
        this.opportunity = opportunity;
    }

    public Contact(String name, String email, String companyName, String phoneNumber, @javax.validation.constraints.NotNull Account account, Opportunity opportunity) {
        this.name = name;
        this.email = email;
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
        this.account = account;
        this.opportunity = opportunity;
    }

    public Contact(Integer id, String name, String email, String companyName, String phoneNumber, @javax.validation.constraints.NotNull Account account) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
        this.account = account;
    }

    public Contact(String name, String email, String companyName, String phoneNumber, @javax.validation.constraints.NotNull Account account) {
        this.name = name;
        this.email = email;
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
        this.account = account;
    }

    public Contact(Integer id, String name, String email, String companyName, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
    }

    public Contact(String name, String email, String companyName, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public @javax.validation.constraints.NotNull Account getAccount() {
        return account;
    }

    public void setAccount(@javax.validation.constraints.NotNull Account account) {
        this.account = account;
    }

    public Opportunity getOpportunity() {
        return opportunity;
    }

    public void setOpportunity(Opportunity opportunity) {
        this.opportunity = opportunity;
    }
}
