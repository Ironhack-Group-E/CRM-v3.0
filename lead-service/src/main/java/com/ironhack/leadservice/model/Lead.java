package com.ironhack.leadservice.model;

import javax.persistence.*;

@Entity
@Table(name = "leads")
public class Lead {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String companyName;
    private String phoneNumber;
//    @ManyToOne
//    private SalesRep salesRep;
    private Integer salesRepId;

    public Lead() {
    }

    public Lead(Integer id, String name, String email, String companyName, String phoneNumber, Integer salesRepId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
        this.salesRepId = salesRepId;
    }

    public Lead(Integer id, String name, String email, String companyName, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
    }

    public Lead(String name, String email, String companyName, String phoneNumber, Integer salesRepId) {
        this.name = name;
        this.email = email;
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
        this.salesRepId = salesRepId;
    }

    public Lead(String name, String email, String companyName, String phoneNumber) {
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

    public Integer getSalesRepId() {
        return salesRepId;
    }

    public void setSalesRepId(Integer salesRepId) {
        this.salesRepId = salesRepId;
    }

    //    public SalesRep getSalesRep() {
//        return salesRep;
//    }
//
//    public String getSalesRepToPrint() {
//        return "SalesRep: " + salesRep.getName();
//    }
//
//    public void setSalesRep(SalesRep salesRep) {
//        this.salesRep = salesRep;
//    }
}
