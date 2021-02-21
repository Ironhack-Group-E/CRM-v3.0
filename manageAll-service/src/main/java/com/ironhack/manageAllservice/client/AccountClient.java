package com.ironhack.manageAllservice.client;

import com.ironhack.manageAllservice.controller.dtos.AccountDTO;
import com.ironhack.manageAllservice.controller.dtos.ContactDTO;
import com.ironhack.manageAllservice.controller.dtos.OpportunityDTO;
import com.ironhack.manageAllservice.controller.dtos.PurchaseDTO;
import com.ironhack.manageAllservice.controller.dtos.report.OpportunityBySalesRepDTO;
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

    @GetMapping("/report/opportunity/by/salesrep")
    List<OpportunityBySalesRepDTO> reportOpportunityBySalesRep();

    @GetMapping("/report/opportunity/closed-won/by/salesrep")
    List<OpportunityBySalesRepDTO> reportOpportunityClosedWonBySalesRep();

    @GetMapping("/report/opportunity/closed-lost/by/salesrep")
    List<OpportunityBySalesRepDTO> reportOpportunityClosedLostBySalesRep();

    @GetMapping("/report/opportunity/open/by/salesrep")
    List<OpportunityBySalesRepDTO> reportOpportunityOpenBySalesRep();
}
