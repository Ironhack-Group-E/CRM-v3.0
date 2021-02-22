package com.ironhack.accountservice.controller.impl;

import com.ironhack.accountservice.controller.dtos.*;
import com.ironhack.accountservice.controller.dtos.report.OpportunityBySalesRepDTO;
import com.ironhack.accountservice.controller.interfaces.IOpportunityController;
import com.ironhack.accountservice.enums.Status;
import com.ironhack.accountservice.model.*;
import com.ironhack.accountservice.service.interfaces.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.HashMap;
import java.util.List;

@RestController
public class OpportunityController implements IOpportunityController {

    @Autowired
    IOpportunityService opportunityService;

    @GetMapping("/opportunity/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OpportunityDTO getOpportunity(@PathVariable Integer id) {
        return opportunityService.getOpportunity(id);
    }

    @GetMapping("/opportunity")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunityDTO> getAllOpportunity() {
        return opportunityService.getAllOpportunity();
    }

    @PostMapping("/opportunity/{leadId}/{accountId}")
    @ResponseStatus(HttpStatus.CREATED)
    public OpportunityDTO postOpportunity(@PathVariable ("leadId") Integer leadId,
                                       @PathVariable ("accountId") Integer accountId,
                                       @RequestBody PurchaseDTO purchaseDTO) {
        return opportunityService.postOpportunity(leadId, accountId, purchaseDTO);
    }

    @PostMapping("/opportunity/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public OpportunityDTO closeOpportunity(@PathVariable Integer id, @RequestParam String status) {
        return opportunityService.closeOpportunity(id, status);
    }

    @GetMapping("/report/opportunity/by/salesrep")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunityBySalesRepDTO> reportOpportunityBySalesRep() {
        return opportunityService.reportOpportunityBySalesRep();
    }

    @GetMapping("/report/opportunity/closed-won/by/salesrep")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunityBySalesRepDTO> reportOpportunityClosedWonBySalesRep() {
        return opportunityService.reportOpportunityClosedWonBySalesRep();
    }

    @GetMapping("/report/opportunity/closed-lost/by/salesrep")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunityBySalesRepDTO> reportOpportunityClosedLostBySalesRep() {
        return opportunityService.reportOpportunityClosedLostBySalesRep();
    }

    @GetMapping("/report/opportunity/open/by/salesrep")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunityBySalesRepDTO> reportOpportunityOpenBySalesRep() {
        return opportunityService.reportOpportunityOpenBySalesRep();
    }

    @GetMapping("/opportunity/max-quantity")
    @ResponseStatus(HttpStatus.OK)
    public Integer getMaxQuantity() {
        return opportunityService.getMaxQuantity();
    }

    @GetMapping("/opportunity/min-quantity")
    @ResponseStatus(HttpStatus.OK)
    public Integer getMinQuantity() {
        return opportunityService.getMinQuantity();
    }

    @GetMapping("/opportunity/mean-quantity")
    @ResponseStatus(HttpStatus.OK)
    public double getMeanQuantity() {
        return opportunityService.getAverageQuantity();
    }

    @GetMapping("/opportunity/median-quantity")
    @ResponseStatus(HttpStatus.OK)
    public double getMedianQuantity() {
        return opportunityService.getMedianQuantity();
    }
}
