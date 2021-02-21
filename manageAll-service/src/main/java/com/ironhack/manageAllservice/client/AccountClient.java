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
    AccountDTO createAccount(@RequestBody AccountDTO accountDTO);

    @GetMapping("/contact/{id}")
    ContactDTO getContact(@PathVariable Integer id);

    @GetMapping("/contact")
    List<ContactDTO> getAllContact();

    @GetMapping("/opportunity/{id}")
    OpportunityDTO getOpportunity(@PathVariable Integer id);

    @GetMapping("/opportunity")
    List<OpportunityDTO> getAllOpportunity();

    @PostMapping("/opportunity/{leadId}/{accountId}")
    OpportunityDTO createOpportunity(@PathVariable ("leadId") Integer leadId,
                                     @PathVariable ("accountId") Integer accountId,
                                     @RequestBody PurchaseDTO purchaseDTO);

    @PostMapping("/opportunity/{id}")
    OpportunityDTO closeOpportunity(@PathVariable Integer id, @RequestParam String status);

    @GetMapping("/account/max-employee-count")
    public Integer getMaxEmployeeCount();

    @GetMapping("/account/min-employee-count")
    public Integer getMinEmployeeCount();

    @GetMapping("/account/mean-employee-count")
    public double getMeanEmployeeCount();

    @GetMapping("/account/median-employee-count")
    public double getMedianEmployeeCount();

    @GetMapping("/account/max-oppos")
    public Integer getMaxOpportunitiesPerAccount();

    @GetMapping("/account/min-oppos")
    public Integer getMinOpportunitiesPerAccount();

    @GetMapping("/account/mean-oppos")
    public double getMeanOpportunitiesPerAccount();

    @GetMapping("/account/median-oppos")
    public double getMedianOpportunitiesPerAccount();

    @GetMapping("/opportunity/max-quantity")
    public Integer getMaxQuantity();

    @GetMapping("/opportunity/min-quantity")
    public Integer getMinQuantity();

    @GetMapping("/opportunity/mean-quantity")
    public double getMeanQuantity();

    @GetMapping("/opportunity/median-quantity")
    public double getMedianQuantity();
}
