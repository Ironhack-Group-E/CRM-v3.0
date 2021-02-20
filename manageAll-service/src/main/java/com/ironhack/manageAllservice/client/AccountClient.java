package com.ironhack.manageAllservice.client;

import com.ironhack.manageAllservice.controller.dtos.AccountDTO;
import com.ironhack.manageAllservice.controller.dtos.ContactDTO;
import com.ironhack.manageAllservice.controller.dtos.OpportunityDTO;
import com.ironhack.manageAllservice.controller.dtos.PurchaseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("account-service")
public interface AccountClient {

    @GetMapping("/account/{id}")
    AccountDTO getAccount(@PathVariable Integer id);

    @PostMapping("/account")
    AccountDTO postAccount(@RequestBody AccountDTO accountDTO);

    @GetMapping("/contact/{id}")
    ContactDTO getContact(@PathVariable Integer id);

    @GetMapping("/opportunity/{id}")
    OpportunityDTO getOpportunity(@PathVariable Integer id);

    @PostMapping("/opportunity/{leadId}/{accountId}")
    OpportunityDTO postOpportunity(@PathVariable ("leadId") Integer leadId,
                                          @PathVariable ("accountId") Integer accountId,
                                          @RequestBody PurchaseDTO purchaseDTO);
}
