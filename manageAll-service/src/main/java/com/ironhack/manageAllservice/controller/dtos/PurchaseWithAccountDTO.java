package com.ironhack.manageAllservice.controller.dtos;

import javax.validation.constraints.NotNull;

public class PurchaseWithAccountDTO {
    @NotNull
    AccountDTO account;
    @NotNull
    PurchaseDTO purchase;

    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }

    public PurchaseDTO getPurchase() {
        return purchase;
    }

    public void setPurchase(PurchaseDTO purchase) {
        this.purchase = purchase;
    }
}
