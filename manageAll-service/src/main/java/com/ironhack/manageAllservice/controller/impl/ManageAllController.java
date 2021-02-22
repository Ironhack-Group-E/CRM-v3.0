package com.ironhack.manageAllservice.controller.impl;

import com.ironhack.manageAllservice.controller.dtos.*;
import com.ironhack.manageAllservice.controller.dtos.report.*;
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
    public List<ReportDTO> reportLeadBySalesRep() {
        return manageAllService.reportLeadBySalesRep();
    }

    @GetMapping("/report/opportunity/by/salesrep")
    @ResponseStatus(HttpStatus.OK)
    public List<ReportDTO> reportOpportunityBySalesRep() {
        return manageAllService.reportOpportunityBySalesRep();
    }

    @GetMapping("/report/opportunity/closed-won/by/salesrep")
    @ResponseStatus(HttpStatus.OK)
    public List<ReportDTO> reportOpportunityClosedWonBySalesRep() {
        return manageAllService.reportOpportunityClosedWonBySalesRep();
    }

    @GetMapping("/report/opportunity/closed-lost/by/salesrep")
    @ResponseStatus(HttpStatus.OK)
    public List<ReportDTO> reportOpportunityClosedLostBySalesRep() {
        return manageAllService.reportOpportunityClosedLostBySalesRep();
    }

    @GetMapping("/report/opportunity/open/by/salesrep")
    @ResponseStatus(HttpStatus.OK)
    public List<ReportDTO> reportOpportunityOpenBySalesRep() {
        return manageAllService.reportOpportunityOpenBySalesRep();
    }

    //empieza by product

    @GetMapping("/report/opportunity/by/product")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunityByProductDTO> reportOpportunityByProduct() {
        return manageAllService.reportOpportunityByProduct();
    }

    @GetMapping("/report/opportunity/closed-won/by/product")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunityByProductDTO> reportOpportunityClosedWonByProduct() {
        return manageAllService.reportOpportunityClosedWonByProduct();
    }

    @GetMapping("/report/opportunity/closed-lost/by/product")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunityByProductDTO> reportOpportunityClosedLostByProduct() {
        return manageAllService.reportOpportunityClosedLostByProduct();
    }

    @GetMapping("/report/opportunity/open/by/product")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunityByProductDTO> reportOpportunityOpenByProduct() {
        return manageAllService.reportOpportunityOpenByProduct();
    }

    //acaba by product

    @GetMapping("/report/max/employee-count")
    @ResponseStatus(HttpStatus.OK)
    public Integer reportMaxEmployeeCount() {
        return manageAllService.reportMaxEmployeeCount();
    }

    @GetMapping("/report/min/employee-count")
    @ResponseStatus(HttpStatus.OK)
    public Integer reportMinEmployeeCount() {
        return manageAllService.reportMinEmployeeCount();
    }

    @GetMapping("/report/mean/employee-count")
    @ResponseStatus(HttpStatus.OK)
    public double reportMeanEmployeeCount() {
        return manageAllService.reportMeanEmployeeCount();
    }

    @GetMapping("/report/median/employee-count")
    @ResponseStatus(HttpStatus.OK)
    public double reportMedianEmployeeCount() {
        return manageAllService.reportMedianEmployeeCount();
    }

    @GetMapping("/report/max/opportunities/per/account")
    @ResponseStatus(HttpStatus.OK)
    public Integer reportMaxOpportunitiesPerAccount() {
        return manageAllService.reportMaxOpportunitiesPerAccount();
    }

    @GetMapping("/report/min/opportunities/per/account")
    @ResponseStatus(HttpStatus.OK)
    public Integer reportMinOpportunitiesPerAccount() {
        return manageAllService.reportMinOpportunitiesPerAccount();
    }

    @GetMapping("/report/mean/opportunities/per/account")
    @ResponseStatus(HttpStatus.OK)
    public double reportMeanOpportunitiesPerAccount() {
        return manageAllService.reportMeanOpportunitiesPerAccount();
    }

    @GetMapping("/report/median/opportunities/per/account")
    @ResponseStatus(HttpStatus.OK)
    public double reportMedianOpportunitiesPerAccount() {
        return manageAllService.reportMedianOpportunitiesPerAccount();
    }

    @GetMapping("/report/max/quantity")
    @ResponseStatus(HttpStatus.OK)
    public Integer reportMaxQuantity() {
        return manageAllService.reportMaxQuantity();
    }

    @GetMapping("/report/min/quantity")
    @ResponseStatus(HttpStatus.OK)
    public Integer reportMinQuantity() {
        return manageAllService.reportMinQuantity();
    }

    @GetMapping("/report/mean/quantity")
    @ResponseStatus(HttpStatus.OK)
    public double reportMeanQuantity() {
        return manageAllService.reportMeanQuantity();
    }

    @GetMapping("/report/median/quantity")
    @ResponseStatus(HttpStatus.OK)
    public double reportMedianQuantity() {
        return manageAllService.reportMedianQuantity();
    }

}
