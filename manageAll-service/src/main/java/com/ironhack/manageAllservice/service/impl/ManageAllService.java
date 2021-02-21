package com.ironhack.manageAllservice.service.impl;

import com.ironhack.manageAllservice.client.AccountClient;
import com.ironhack.manageAllservice.client.LeadClient;
import com.ironhack.manageAllservice.client.SalesRepClient;
import com.ironhack.manageAllservice.controller.dtos.*;
import com.ironhack.manageAllservice.controller.dtos.report.LeadBySalesRepDTO;
import com.ironhack.manageAllservice.service.interfaces.IManageAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ManageAllService implements IManageAllService {

    @Autowired
    private LeadClient leadClient;

    @Autowired
    private SalesRepClient salesRepClient;

    @Autowired
    private AccountClient accountClient;

    private CircuitBreakerFactory circuitBreakerFactory = new Resilience4JCircuitBreakerFactory();

    /* ---------------------------------- SALES REP SERVICE-------------------------------------*/

    public SalesRepDTO newSalesRep(SalesRepDTO salesRepDTO) {

        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("salesRep-service");

        salesRepDTO.setId(null);

        SalesRepDTO salesRepDTO2 = circuitBreaker.run(() -> salesRepClient.postSalesRep(salesRepDTO),
                throwable -> postSalesRepFallBack());

        return salesRepDTO2;
    }

    public List<SalesRepDTO> showSalesRep() {

        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("salesRep-service");

        List<SalesRepDTO> salesRepDTOList = circuitBreaker.run(()->salesRepClient.getAllSalesRep(),
                throwable -> getAllSalesRepFallBack());

        return salesRepDTOList;
    }

    public SalesRepDTO getSalesRepById(Integer id) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("salesRep-service");

        SalesRepDTO salesRepDTO = circuitBreaker.run(()->salesRepClient.getSalesRep(id),
                throwable -> postSalesRepFallBack());
        return salesRepDTO;
    }

    /* ---------------------------------- LEAD SERVICE-------------------------------------*/

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

    public List<LeadDTO> getLeads() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("lead-service");

        List<LeadDTO> leadDTOList = circuitBreaker.run(()->leadClient.getAll(),
                throwable -> getAllLeadsFallback());

        return leadDTOList;
    }

    public LeadDTO getLeadById(Integer id) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("lead-service");

        LeadDTO leadDTO = circuitBreaker.run(()->leadClient.getById(id), throwable -> getLeadFallback());

        return leadDTO;
    }

    public LeadDTO deleteLead(Integer id) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("lead-service");
        LeadDTO leadDTO = circuitBreaker.run(
                () -> leadClient.delete(id),
                throwable -> deleteLeadFallback());

        return leadDTO;
    }

    public OpportunityDTO convertLead(Integer id, Integer idAccount, PurchaseDTO purchaseDTO) {
        LeadDTO leadDTO = getLeadById(id);
        AccountDTO accountDTO = lookUpAccount(idAccount);

        OpportunityDTO opportunityDTO = createOpportunity(leadDTO.getId(), accountDTO.getId(), purchaseDTO);

        deleteLead(leadDTO.getId());

        return opportunityDTO;
    }

    public OpportunityDTO convertLead(Integer id, PurchaseWithAccountDTO purchaseWithAccountDTO) {
        LeadDTO leadDTO = getLeadById(id);

        AccountDTO accountDTO = purchaseWithAccountDTO.getAccount();
        accountDTO.setCompanyName(leadDTO.getCompanyName());

        AccountDTO newAccountDTO = createAccount(accountDTO);

        return convertLead(leadDTO.getId(), newAccountDTO.getId(), purchaseWithAccountDTO.getPurchase());
    }

    /* ---------------------------------- OPPORTUNITY SERVICE -------------------------------------*/

    public List<OpportunityDTO> showOpportunity() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("opportunity-service");

        List<OpportunityDTO> opportunityDTOList = circuitBreaker.run(()->accountClient.getAllOpportunity(),
                throwable -> getAllOpportunityFallback());

        return opportunityDTOList;
    }

    public OpportunityDTO lookUpOpportunity(Integer id) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("opportunity-service");

        OpportunityDTO opportunityDTO = circuitBreaker.run(()->accountClient.getOpportunity(id),
                throwable -> getOpportunityFallback());
        return opportunityDTO;
    }

    public OpportunityDTO closeOpportunity(Integer id, String status) {

        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");

        OpportunityDTO opportunityDTO = circuitBreaker.run(() -> accountClient.closeOpportunity(id, status),
                throwable -> closeOpportunityFallBack());

        return opportunityDTO;
    }

    public OpportunityDTO createOpportunity(Integer idLead, Integer idAccount, PurchaseDTO purchaseDTO) {
        CircuitBreaker circuitBreaker2 = circuitBreakerFactory.create("opportunity-service");

        OpportunityDTO opportunityDTO =
                circuitBreaker2.run(
                        () -> accountClient.createOpportunity(idLead, idAccount, purchaseDTO),
                        throwable -> createOpportunityFallback());

        return opportunityDTO;
    }


    /* ---------------------------------- ACCOUNT SERVICE -------------------------------------*/

    public List<AccountDTO> showAccount() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");

        List<AccountDTO> accountDTOList = circuitBreaker.run(()->accountClient.getAllAccount(),
                throwable -> getAllAccountsFallback());

        return accountDTOList;
    }

    public AccountDTO lookUpAccount(Integer id) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");

        AccountDTO accountDTO = circuitBreaker.run(()->accountClient.getAccount(id),
                throwable -> getAccountFallback());

        return accountDTO;
    }

    public AccountDTO createAccount(AccountDTO accountDTO) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");

        AccountDTO newAccountDTO = circuitBreaker.run(
                () -> accountClient.createAccount(accountDTO),
                throwable -> createAccountFallback());

        return newAccountDTO;
    }

    /* ---------------------------------- CONTACT SERVICE -------------------------------------*/


    public List<ContactDTO> showContact() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("contact-service");

        List<ContactDTO> contactDTOList = circuitBreaker.run(()->accountClient.getAllContact(),
                throwable -> getAllContactFallback());

        return contactDTOList;
    }


    public ContactDTO lookUpContact(Integer id) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("contact-service");

        ContactDTO contactDTO = circuitBreaker.run(()-> accountClient.getContact(id),
                throwable -> getContactFallback());
        return contactDTO;
    }


    /* ---------------------------------- REPORTS SERVICE -------------------------------------*/

    public List<LeadBySalesRepDTO> reportLeadBySalesRep() {

        HashMap<Integer, Integer> totals = new HashMap<>();
        HashMap<Integer, String> names = new HashMap<>();

        List<LeadDTO> leadDTOList = getLeads();
        for(LeadDTO leadDTO : leadDTOList) {
            SalesRepDTO salesRepDTO = getSalesRepById(leadDTO.getSalesRepId());
            Integer id = salesRepDTO.getId();
            if(!totals.containsKey(id)) {
                totals.put(id, 0);
                names.put(id, salesRepDTO.getName());
            }
            totals.put(id, totals.get(id) + 1);
        }

        List<LeadBySalesRepDTO> resultList = new ArrayList<>();
        for(Integer id : totals.keySet()) {
            LeadBySalesRepDTO leadBySalesRepDTO = new LeadBySalesRepDTO();
            leadBySalesRepDTO.setName(names.get(id));
            leadBySalesRepDTO.setCount(totals.get(id));
            resultList.add(leadBySalesRepDTO);
        }

        return resultList;
    }


    /* --------------------------------- FALLBACK METHODS ----------------------------*/

    private SalesRepDTO getSalesRepFallBack() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "SalesRep service not available or salesRep not found");
    }

    private List<SalesRepDTO> getAllSalesRepFallBack() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "SaleRep service not available");
    }

    private SalesRepDTO postSalesRepFallBack() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "SaleRep service not available");
    }

    private LeadDTO addLeadFallBack() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Lead service not available");
    }

    private OpportunityDTO closeOpportunityFallBack() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Account service not available");
    }

    private List<LeadDTO> getAllLeadsFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Lead service not available");
    }

    private List<OpportunityDTO> getAllOpportunityFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Account service not available");

    }

    private OpportunityDTO getOpportunityFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Account service not available");

    }

    private List<AccountDTO> getAllAccountsFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Account service not available");
    }

    private AccountDTO getAccountFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Account service not available");
    }

    private List<ContactDTO> getAllContactFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Account service not available");

    }

    private ContactDTO getContactFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Account service not available");

    }

    private LeadDTO getLeadFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Account service not available");

    }

    private AccountDTO createAccountFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Account service not available");

    }

    private OpportunityDTO createOpportunityFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Account service not available");

    }

    private LeadDTO deleteLeadFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Account service not available");

    }

}
