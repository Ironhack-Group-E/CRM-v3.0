package com.ironhack.manageAllservice.controller.dtos.report;

public class OpportunitiesByCityDTO {
    private String city;
    private Long count;

    public OpportunitiesByCityDTO(String city, Long count) {
        this.city = city;
        this.count = count;
    }

    public OpportunitiesByCityDTO() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
