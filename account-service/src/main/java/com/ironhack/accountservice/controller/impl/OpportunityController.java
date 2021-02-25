package com.ironhack.accountservice.controller.impl;

import com.ironhack.accountservice.controller.dtos.*;
import com.ironhack.accountservice.controller.dtos.report.*;
import com.ironhack.accountservice.controller.interfaces.*;
import com.ironhack.accountservice.service.interfaces.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class OpportunityController implements IOpportunityController {

    @Autowired
    IOpportunityService opportunityService;

    /*-----------------------------------GENERAL ---------------------------------------- */

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


    /*-----------------------------------REPORT BY SALESREP---------------------------------------- */


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

    /*-----------------------------------REPORT BY PRODUCT---------------------------------------- */


    @GetMapping("/report/opportunity/by/product")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunityByProductDTO> reportOpportunityByProduct() {
        return opportunityService.reportOpportunityByProduct();
    }

    @GetMapping("/report/opportunity/closed-won/by/product")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunityByProductDTO> reportOpportunityClosedWonByProduct() {
        return opportunityService.reportOpportunityClosedWonByProduct();
    }

    @GetMapping("/report/opportunity/closed-lost/by/product")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunityByProductDTO> reportOpportunityClosedLostByProduct() {
        return opportunityService.reportOpportunityClosedLostByProduct();
    }

    @GetMapping("/report/opportunity/open/by/product")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunityByProductDTO> reportOpportunityOpenByProduct() {
        return opportunityService.reportOpportunityOpenByProduct();
    }

    /*-----------------------------------REPORT BY COUNTRY---------------------------------------- */

    @GetMapping("/report/opportunity/by/country")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunitiesByCountryDTO> reportOpportunityByCountry() {
        return opportunityService.reportOpportunityByCountry();
    }

    @GetMapping("/report/opportunity/closed-won/by/country")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunitiesByCountryDTO> reportOpportunityClosedWonByCountry() {
        return opportunityService.reportOpportunityClosedWonByCountry();
    }

    @GetMapping("/report/opportunity/closed-lost/by/country")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunitiesByCountryDTO> reportOpportunityClosedLostByCountry() {
        return opportunityService.reportOpportunityClosedLostByCountry();
    }

    @GetMapping("/report/opportunity/open/by/country")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunitiesByCountryDTO> reportOpportunityOpenByCountry() {
        return opportunityService.reportOpportunityOpenByCountry();
    }

    /*-----------------------------------REPORT BY CITY---------------------------------------- */

    @GetMapping("/report/opportunity/by/city")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunitiesByCityDTO> reportOpportunityByCity(){
        return opportunityService.reportOpportunityByCity();
    }

    @GetMapping("/report/opportunity/closed-won/by/city")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunitiesByCityDTO> reportOpportunityClosedWonByCity(){
        return opportunityService.reportOpportunityClosedWonByCity();
    }

    @GetMapping("/report/opportunity/closed-lost/by/city")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunitiesByCityDTO> reportOpportunityClosedLostByCity(){
        return opportunityService.reportOpportunityClosedLostByCity();
    }

    @GetMapping("/report/opportunity/open/by/city")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunitiesByCityDTO> reportOpportunityOpenByCity(){
        return opportunityService.reportOpportunityOpenByCity();
    }


    /*-----------------------------------REPORT BY INDUSTRY---------------------------------------- */

    @GetMapping("/report/opportunity/by/industry")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunitiesByIndustryDTO> reportOpportunityByIndustry(){
        return opportunityService.reportOpportunityByIndustry();
    }

    @GetMapping("/report/opportunity/closed-won/by/industry")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunitiesByIndustryDTO> reportOpportunityClosedWonByIndustry(){
        return opportunityService.reportOpportunityClosedWonByIndustry();
    }

    @GetMapping("/report/opportunity/closed-lost/by/industry")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunitiesByIndustryDTO> reportOpportunityClosedLostByIndustry(){
        return opportunityService.reportOpportunityClosedLostByIndustry();
    }

    @GetMapping("/report/opportunity/open/by/industry")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunitiesByIndustryDTO> reportOpportunityOpenByIndustry(){
        return opportunityService.reportOpportunityOpenByIndustry();
    }

    /*-----------------------------------QUANTITY STATES---------------------------------------- */

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
