package com.ironhack.manageAllservice.client;

import com.ironhack.manageAllservice.controller.dtos.AccountDTO;
import com.ironhack.manageAllservice.controller.dtos.ContactDTO;
import com.ironhack.manageAllservice.controller.dtos.OpportunityDTO;
import com.ironhack.manageAllservice.controller.dtos.PurchaseDTO;
import com.ironhack.manageAllservice.controller.dtos.report.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.*;

@FeignClient("account-service")
public interface AccountClient {

    /*----------------------------------------- ACCOUNT --------------------------------------*/

    @GetMapping("/account/{id}")
    AccountDTO getAccount(@PathVariable Integer id);

    @GetMapping("/account")
    List<AccountDTO> getAllAccount();

    @PostMapping("/account")
    AccountDTO createAccount(@RequestBody AccountDTO accountDTO);

    /*-------------------------------------- CONTACT ------------------------------------------*/

    @GetMapping("/contact/{id}")
    ContactDTO getContact(@PathVariable Integer id);

    @GetMapping("/contact")
    List<ContactDTO> getAllContact();

    /*-------------------------------------- OPPORTUNITY ---------------------------------------*/


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

    /*--------------------------------------- REPORTS -----------------------------------------*/
    /*------------------------------------ BY SALES REP ---------------------------------------*/

    @GetMapping("/report/opportunity/by/salesrep")
    List<OpportunityBySalesRepDTO> reportOpportunityBySalesRep();

    @GetMapping("/report/opportunity/closed-won/by/salesrep")
    List<OpportunityBySalesRepDTO> reportOpportunityClosedWonBySalesRep();

    @GetMapping("/report/opportunity/closed-lost/by/salesrep")
    List<OpportunityBySalesRepDTO> reportOpportunityClosedLostBySalesRep();

    @GetMapping("/report/opportunity/open/by/salesrep")
    List<OpportunityBySalesRepDTO> reportOpportunityOpenBySalesRep();

    /*------------------------------------ BY PRODUCT ---------------------------------------*/

    @GetMapping("/report/opportunity/by/product")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunityByProductDTO> reportOpportunityByProduct();

    @GetMapping("/report/opportunity/closed-won/by/product")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunityByProductDTO> reportOpportunityClosedWonByProduct();

    @GetMapping("/report/opportunity/closed-lost/by/product")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunityByProductDTO> reportOpportunityClosedLostByProduct();

    @GetMapping("/report/opportunity/open/by/product")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunityByProductDTO> reportOpportunityOpenByProduct();


    /*------------------------------------ BY COUNTRY ---------------------------------------*/

    @GetMapping("/report/opportunity/by/country")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunitiesByCountryDTO> reportOpportunityByCountry();

    @GetMapping("/report/opportunity/closed-won/by/country")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunitiesByCountryDTO> reportOpportunityClosedWonByCountry();

    @GetMapping("/report/opportunity/closed-lost/by/country")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunitiesByCountryDTO> reportOpportunityClosedLostByCountry();

    @GetMapping("/report/opportunity/open/by/country")
    @ResponseStatus(HttpStatus.OK)
    public List<OpportunitiesByCountryDTO> reportOpportunityOpenByCountry();


    /*------------------------------------ BY CITY ---------------------------------------*/

    @GetMapping("/report/opportunity/by/city")
    public List<OpportunitiesByCityDTO> reportOpportunityByCity();

    @GetMapping("/report/opportunity/closed-won/by/city")
    public List<OpportunitiesByCityDTO> reportOpportunityClosedWonByCity();

    @GetMapping("/report/opportunity/closed-lost/by/city")
    public List<OpportunitiesByCityDTO> reportOpportunityClosedLostByCity();

    @GetMapping("/report/opportunity/open/by/city")
    public List<OpportunitiesByCityDTO> reportOpportunityOpenByCity();


    /*------------------------------------ BY INDUSTRY ---------------------------------------*/

    @GetMapping("/report/opportunity/by/industry")
    public List<OpportunitiesByIndustryDTO> reportOpportunityByIndustry();

    @GetMapping("/report/opportunity/closed-won/by/industry")
    public List<OpportunitiesByIndustryDTO> reportOpportunityClosedWonByIndustry();

    @GetMapping("/report/opportunity/closed-lost/by/industry")
    public List<OpportunitiesByIndustryDTO> reportOpportunityClosedLostByIndustry();

    @GetMapping("/report/opportunity/open/by/industry")
    public List<OpportunitiesByIndustryDTO> reportOpportunityOpenByIndustry();


    /*------------------------------------ EMPLOYEE STATS ---------------------------------------*/

    @GetMapping("/account/max-employee-count")
    public Integer getMaxEmployeeCount();

    @GetMapping("/account/min-employee-count")
    public Integer getMinEmployeeCount();

    @GetMapping("/account/mean-employee-count")
    public double getMeanEmployeeCount();

    @GetMapping("/account/median-employee-count")
    public double getMedianEmployeeCount();

    /*------------------------------------ EMPLOYEE STATS ---------------------------------------*/


    @GetMapping("/account/max-oppos")
    public Integer getMaxOpportunitiesPerAccount();

    @GetMapping("/account/min-oppos")
    public Integer getMinOpportunitiesPerAccount();

    @GetMapping("/account/mean-oppos")
    public double getMeanOpportunitiesPerAccount();

    @GetMapping("/account/median-oppos")
    public double getMedianOpportunitiesPerAccount();

    /*------------------------------------ QUANTITY STATS ---------------------------------------*/


    @GetMapping("/opportunity/max-quantity")
    public Integer getMaxQuantity();

    @GetMapping("/opportunity/min-quantity")
    public Integer getMinQuantity();

    @GetMapping("/opportunity/mean-quantity")
    public double getMeanQuantity();

    @GetMapping("/opportunity/median-quantity")
    public double getMedianQuantity();
}
