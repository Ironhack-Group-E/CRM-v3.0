package com.ironhack.accountservice.controller.dtos.report;

public class OpportunityBySalesRepDTO {
    private Integer salesRepId;
    private Long count;

    public OpportunityBySalesRepDTO() {
    }

    public OpportunityBySalesRepDTO(Integer salesRepId, Long count) {
        this.salesRepId = salesRepId;
        this.count = count.longValue();
    }

    public Integer getSalesRepId() {
        return salesRepId;
    }

    public void setSalesRepId(Integer salesRepId) {
        this.salesRepId = salesRepId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
