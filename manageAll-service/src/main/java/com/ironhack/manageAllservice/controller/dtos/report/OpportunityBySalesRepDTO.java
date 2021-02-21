package com.ironhack.manageAllservice.controller.dtos.report;

public class OpportunityBySalesRepDTO {
    private Integer salesRepId;
    private Integer count;

    public OpportunityBySalesRepDTO() {
    }

    public OpportunityBySalesRepDTO(Integer salesRepId, Integer count) {
        this.salesRepId = salesRepId;
        this.count = count;
    }

    public Integer getSalesRepId() {
        return salesRepId;
    }

    public void setSalesRepId(Integer salesRepId) {
        this.salesRepId = salesRepId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
