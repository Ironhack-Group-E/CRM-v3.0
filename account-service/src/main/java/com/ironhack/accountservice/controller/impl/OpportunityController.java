package com.ironhack.accountservice.controller.impl;

import com.ironhack.accountservice.controller.dtos.*;
import com.ironhack.accountservice.controller.interfaces.IOpportunityController;
import com.ironhack.accountservice.model.*;
import com.ironhack.accountservice.service.interfaces.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
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
}
