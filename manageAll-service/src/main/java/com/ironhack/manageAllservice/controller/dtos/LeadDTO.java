package com.ironhack.manageAllservice.controller.dtos;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class LeadDTO {
    private Integer id;
    @NotEmpty
    @Pattern(regexp = "^[ÁÉÍÓÚA-ZÑ]?[a-záéíóúñ]+(\\s+[ÁÉÍÓÚA-ZÑ]?[a-záéíóúñ]+)*${1,31}")
    private String name;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Length(max = 60)
    private String companyName;
    @NotEmpty
    @Pattern(regexp = "^\\s*(?:\\+?(\\d{1,3}))?([-. (]*(\\d{3})[-. )]*)?((\\d{3})[-. ]*(\\d{2,4})(?:[-.x ]*(\\d+))?)\\s*$")
    private String phoneNumber;
    @Min(1)
    private Integer salesRepId;

    public LeadDTO() {
    }

    public LeadDTO(String name, String email, String companyName, String phoneNumber, Integer salesRepId) {
        this.name = name;
        this.email = email;
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
        this.salesRepId = salesRepId;
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
}
