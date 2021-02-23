package com.ironhack.manageAllservice.controller.dtos.report;

import com.ironhack.manageAllservice.enums.Industry;

public class OpportunitiesByIndustryDTO {
    private Industry industry;
    private Long count;

    public OpportunitiesByIndustryDTO(String industry, Long count) {
        this.industry = Industry.valueOf(industry);
        this.count = count;
    }

    public OpportunitiesByIndustryDTO() {
    }

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
