package com.ironhack.manageAllservice.service.impl;

import com.ironhack.manageAllservice.client.AccountClient;
import com.ironhack.manageAllservice.client.LeadClient;
import com.ironhack.manageAllservice.client.SalesRepClient;
import com.ironhack.manageAllservice.controller.dtos.LeadDTO;
import com.ironhack.manageAllservice.controller.dtos.OpportunityDTO;
import com.ironhack.manageAllservice.controller.dtos.SalesRepDTO;
import com.ironhack.manageAllservice.service.interfaces.IManageAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ManageAllService implements IManageAllService {

    @Autowired
    private LeadClient leadClient;

    @Autowired
    private SalesRepClient salesRepClient;

    @Autowired
    private AccountClient accountClient;

    private CircuitBreakerFactory circuitBreakerFactory = new Resilience4JCircuitBreakerFactory();

    public SalesRepDTO newSalesRep(SalesRepDTO salesRepDTO) {

        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("salesRep-service");

        salesRepDTO.setId(null);

        SalesRepDTO salesRepDTO2 = circuitBreaker.run(() -> salesRepClient.postSalesRep(salesRepDTO), throwable -> postSalesRepFallBack());

        return salesRepDTO2;
    }

    private SalesRepDTO postSalesRepFallBack() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "SaleRep service not available");
    }

    public LeadDTO newLead(LeadDTO leadDTO) {

        CircuitBreaker circuitBreaker1 = circuitBreakerFactory.create("salesRep-service");
        CircuitBreaker circuitBreaker2 = circuitBreakerFactory.create("lead-service");

        leadDTO.setId(null);

        Integer salesRepId = leadDTO.getSalesRepId();

        //Check if the salesRep-service is up and the salesRep exists
        circuitBreaker1.run(() -> salesRepClient.getSalesRep(salesRepId), throwable -> getSalesRepFallBack());

        //Check if the lead-service is up and, if it's, save the lead
        LeadDTO leadDTO2 = circuitBreaker2.run(() -> leadClient.add(leadDTO), throwable -> addLeadFallBack());

        return leadDTO2;
    }

    private SalesRepDTO getSalesRepFallBack() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "SalesRep service not available or salesRep not found");
    }

    private LeadDTO addLeadFallBack() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Lead service not available");
    }

    public OpportunityDTO closeOpportunity(Integer id, String status) {

        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");

        OpportunityDTO opportunityDTO = circuitBreaker.run(() -> accountClient.closeOpportunity(id, status),
                throwable -> closeOpportunityFallBack());

        return opportunityDTO;
    }

    private OpportunityDTO closeOpportunityFallBack() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Account service not available");
    }
}
