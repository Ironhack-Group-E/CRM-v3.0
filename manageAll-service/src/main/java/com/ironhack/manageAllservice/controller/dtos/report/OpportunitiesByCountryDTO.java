package com.ironhack.manageAllservice.controller.dtos.report;

public class OpportunitiesByCountryDTO {
    private String country;
    private Long count;

    public OpportunitiesByCountryDTO() {
    }

    public OpportunitiesByCountryDTO(String country, Long count) {
        this.country =country;
        this.count = count;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
