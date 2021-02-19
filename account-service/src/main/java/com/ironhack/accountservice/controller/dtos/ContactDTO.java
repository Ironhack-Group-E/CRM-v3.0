package com.ironhack.accountservice.controller.dtos;

import com.ironhack.accountservice.model.Account;

public class ContactDTO {

    private Integer id;
    private String name;
    private String email;
    private String companyName;
    private String phoneNumber;
    private Account account;

    public ContactDTO() {
    }

    public ContactDTO(Integer id, String name, String email, String companyName, String phoneNumber, Account account) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
        this.account = account;
    }

    public ContactDTO(String name, String email, String companyName, String phoneNumber, Account account) {
        this.name = name;
        this.email = email;
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
        this.account = account;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
