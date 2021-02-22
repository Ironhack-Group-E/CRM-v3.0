package com.ironhack.manageAllservice.controller.dtos.report;


import com.ironhack.manageAllservice.enums.*;

public class OpportunityByProductDTO {

    private Product product;
    private Long count;

    public OpportunityByProductDTO() {
    }

    public OpportunityByProductDTO(String product, Long count) {
        this.product =Product.valueOf(product);
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
