package com.ironhack.manageAllservice.controller.impl;

import com.ironhack.manageAllservice.controller.dtos.*;
import com.ironhack.manageAllservice.controller.dtos.report.LeadBySalesRepDTO;
import com.ironhack.manageAllservice.controller.interfaces.IManageAllController;
import com.ironhack.manageAllservice.service.interfaces.IManageAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ManageAllController implements IManageAllController {

    @Autowired
    private IManageAllService manageAllService;

    /* ---------------------------------- SALES REP CONTROLLER-------------------------------------*/

    @PostMapping("/new-salesrep")
    @ResponseStatus(HttpStatus.CREATED)
    public SalesRepDTO newSalesRep(@RequestBody @Valid SalesRepDTO salesRepDTO) {
        return manageAllService.newSalesRep(salesRepDTO);
    }

    @GetMapping("/salesrep")
    @ResponseStatus(HttpStatus.OK)
    public List<SalesRepDTO> showSalesRep() {
        return manageAllService.showSalesRep();
    }

    @GetMapping("/salesrep/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SalesRepDTO lookUpSalesRep(@PathVariable Integer id) {
        return manageAllService.getSalesRepById(id);
    }

    /* -------------------------------------- LEAD CONTROLLER---------------------------------------------*/

    @PostMapping("/new-lead")
    @ResponseStatus(HttpStatus.CREATED)
    public LeadDTO newLead(@RequestBody @Valid LeadDTO leadDTO) {
        return manageAllService.newLead(leadDTO);
    }

    @GetMapping("/lead")
    @ResponseStatus(HttpStatus.OK)
    public List<LeadDTO> showLeads() {
        return manageAllService.getLeads();
    }

    @GetMapping("/lead/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LeadDTO lookUpLead(@PathVariable Integer id) {
        return manageAllService.getLeadById(id);
    }

    @PostMapping("/lead/{id}/account/{idAcc}")
    @ResponseStatus(HttpStatus.CREATED)
    public OpportunityDTO convertLead(@PathVariable Integer id, @PathVariable("idAcc") Integer idAccount, @RequestBody PurchaseDTO purchaseDTO) {
        return manageAllService.convertLead(id, idAccount, purchaseDTO);
    }

    @PostMapping("/lead/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public OpportunityDTO convertLead(@PathVariable Integer id, @RequestBody PurchaseWithAccountDTO purchaseWithAccountDTO) {
        return manageAllService.convertLead(id, purchaseWithAccountDTO);
    }

    /* -------------------------------------- OPPORTUNITY CONTROLLER-----------------------------------------*/



    @GetMapping("/opportunity")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunityDTO> showOpportunity() {
        return manageAllService.showOpportunity();
    }

    @GetMapping("/opportunity/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OpportunityDTO lookUpOpportunity(@PathVariable Integer id) {
        return manageAllService.lookUpOpportunity(id);
    }

    /* -------------------------------------- ACCOUNT CONTROLLER-----------------------------------------*/

    @GetMapping("/account")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountDTO> showAccount() {
        return manageAllService.showAccount();
    }

    @GetMapping("/account/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDTO lookUpAccount(@PathVariable Integer id) {
        return manageAllService.lookUpAccount(id);
    }


    /* -------------------------------------- CONTACT CONTROLLER-----------------------------------------*/

    @GetMapping("/contact")
    @ResponseStatus(HttpStatus.OK)
    public List<ContactDTO> showContact() {
        return manageAllService.showContact();
    }

    @GetMapping("/contact/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContactDTO lookUpContact(@PathVariable Integer id) {
        return manageAllService.lookUpContact(id);
    }

    @PostMapping("/close-opportunity/{id}")
    public OpportunityDTO closeOpportunity(@PathVariable Integer id, @RequestParam String status) {
        return manageAllService.closeOpportunity(id, status);
    }


    /* -------------------------------------- REPORTS CONTROLLER -----------------------------------------*/

    @GetMapping("/report/lead/by/salesrep")
    @ResponseStatus(HttpStatus.OK)
    public List<LeadBySalesRepDTO> reportLeadBySalesRep() {
        return manageAllService.reportLeadBySalesRep();
    }
}
