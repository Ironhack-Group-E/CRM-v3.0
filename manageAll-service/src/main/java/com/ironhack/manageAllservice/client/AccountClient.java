package com.ironhack.manageAllservice.client;

import com.ironhack.manageAllservice.controller.dtos.AccountDTO;
import com.ironhack.manageAllservice.controller.dtos.ContactDTO;
import com.ironhack.manageAllservice.controller.dtos.OpportunityDTO;
import com.ironhack.manageAllservice.controller.dtos.PurchaseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.*;

@FeignClient("account-service")
public interface AccountClient {

    @GetMapping("/account/{id}")
    AccountDTO getAccount(@PathVariable Integer id);

    @GetMapping("/account")
    List<AccountDTO> getAllAccount();

    @PostMapping("/account")
    AccountDTO postAccount(@RequestBody AccountDTO accountDTO);

    @GetMapping("/contact/{id}")
    ContactDTO getContact(@PathVariable Integer id);

    @GetMapping("/contact")
    List<ContactDTO> getAllContact();

    @GetMapping("/opportunity/{id}")
    OpportunityDTO getOpportunity(@PathVariable Integer id);

    @GetMapping("/opportunity")
    List<OpportunityDTO> getAllOpportunity();

    @PostMapping("/opportunity/{leadId}/{accountId}")
    OpportunityDTO postOpportunity(@PathVariable ("leadId") Integer leadId,
                                          @PathVariable ("accountId") Integer accountId,
                                          @RequestBody PurchaseDTO purchaseDTO);

    @PutMapping("/opportunity/{id}")
    OpportunityDTO closeOpportunity(@PathVariable Integer id, @RequestParam String status);
}
