package com.ironhack.manageAllservice.controller.dtos;

import com.ironhack.manageAllservice.enums.Industry;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class AccountDTO {
    private Integer id;
    @NotEmpty
    private String companyName;
    private Industry industry;
    @Min(0)
    private int employeeCount;
    @NotEmpty
    private String city;
    @NotEmpty
    private String country;

    public AccountDTO() {
    }

    public AccountDTO(Integer id, String companyName, Industry industry, int employeeCount, String city, String country) {
        this.id = id;
        this.companyName = companyName;
        this.industry = industry;
        this.employeeCount = employeeCount;
        this.city = city;
        this.country = country;
    }

    public AccountDTO(String companyName, Industry industry, int employeeCount, String city, String country) {
        this.companyName = companyName;
        this.industry = industry;
        this.employeeCount = employeeCount;
        this.city = city;
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
