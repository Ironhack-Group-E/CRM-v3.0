package com.ironhack.manageAllservice.controller.dtos;

import com.ironhack.manageAllservice.enums.*;

import javax.validation.constraints.Min;

public class PurchaseDTO {
    private Product product;
    @Min(0)
    private int quantity;

    public PurchaseDTO() {
    }

    public PurchaseDTO(Product product, @Min(0) int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}