package com.ironhack.manageAllservice.controller.impl;

import com.ironhack.manageAllservice.controller.dtos.LeadDTO;
import com.ironhack.manageAllservice.controller.dtos.OpportunityDTO;
import com.ironhack.manageAllservice.controller.dtos.SalesRepDTO;
import com.ironhack.manageAllservice.controller.interfaces.IManageAllController;
import com.ironhack.manageAllservice.service.interfaces.IManageAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ManageAllController implements IManageAllController {

    @Autowired
    private IManageAllService manageAllService;

    @PostMapping("/new-salesrep")
    @ResponseStatus(HttpStatus.CREATED)
    public SalesRepDTO newSalesRep(@RequestBody @Valid SalesRepDTO salesRepDTO) {
        return manageAllService.newSalesRep(salesRepDTO);
    }

    @PostMapping("/new-lead")
    @ResponseStatus(HttpStatus.CREATED)
    public LeadDTO newLead(@RequestBody @Valid LeadDTO leadDTO) {
        return manageAllService.newLead(leadDTO);
    }

    @PutMapping("/close-opportunity/{id}")
    public OpportunityDTO closeOpportunity(@PathVariable Integer id, @RequestParam String status) {
        return manageAllService.closeOpportunity(id, status);
    }
}
