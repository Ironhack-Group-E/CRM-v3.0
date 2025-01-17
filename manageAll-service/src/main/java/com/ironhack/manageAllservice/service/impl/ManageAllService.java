package com.ironhack.manageAllservice.service.impl;

import com.ironhack.manageAllservice.client.AccountClient;
import com.ironhack.manageAllservice.client.LeadClient;
import com.ironhack.manageAllservice.client.SalesRepClient;
import com.ironhack.manageAllservice.controller.dtos.*;
import com.ironhack.manageAllservice.controller.dtos.report.OpportunityBySalesRepDTO;
import com.ironhack.manageAllservice.controller.dtos.report.ReportDTO;
import com.ironhack.manageAllservice.service.exceptions.SalesRepNotFoundException;
import com.ironhack.manageAllservice.controller.dtos.report.*;
import com.ironhack.manageAllservice.service.interfaces.IManageAllService;
import com.ironhack.manageAllservice.utils.PdfGenerator;
import com.itextpdf.text.DocumentException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

@Service
public class ManageAllService implements IManageAllService {

    @Autowired
    private LeadClient leadClient;

    @Autowired
    private SalesRepClient salesRepClient;

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> globalCustomConfiguration() {

        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .slidingWindowSize(2)
                .ignoreExceptions(SalesRepNotFoundException.class)
                .build();
        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(4))
                .build();

        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(circuitBreakerConfig)
                .timeLimiterConfig(timeLimiterConfig)
                .build());
    }


    /* ---------------------------------- SALES REP SERVICE-------------------------------------*/

    public SalesRepDTO newSalesRep(SalesRepDTO salesRepDTO) {

        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");

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
    /* ---------------------------------- BY SALES REP    -------------------------------------*/

    public List<ReportDTO> reportLeadBySalesRep() {

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

        List<ReportDTO> resultList = new ArrayList<>();
        for(Integer id : totals.keySet()) {
            ReportDTO reportDTO = new ReportDTO();
            reportDTO.setName(names.get(id));
            reportDTO.setCount(totals.get(id));
            resultList.add(reportDTO);
        }

        return resultList;
    }

    public List<ReportDTO> reportOpportunityBySalesRep() {

        List<OpportunityBySalesRepDTO> result = accountClient.reportOpportunityBySalesRep();

        List<ReportDTO> resultWithNames = getReportDTOS(result);

        return resultWithNames;
    }

    public List<ReportDTO> reportOpportunityClosedWonBySalesRep() {

        List<OpportunityBySalesRepDTO> result = accountClient.reportOpportunityClosedWonBySalesRep();

        List<ReportDTO> resultWithNames = getReportDTOS(result);

        return resultWithNames;
    }

    public List<ReportDTO> reportOpportunityClosedLostBySalesRep() {

        List<OpportunityBySalesRepDTO> result = accountClient.reportOpportunityClosedLostBySalesRep();

        List<ReportDTO> resultWithNames = getReportDTOS(result);

        return resultWithNames;
    }

    public List<ReportDTO> reportOpportunityOpenBySalesRep() {

        List<OpportunityBySalesRepDTO> result = accountClient.reportOpportunityOpenBySalesRep();

        List<ReportDTO> resultWithNames = getReportDTOS(result);

        return resultWithNames;
    }

    /* ---------------------------------- BY PRODUCT -------------------------------------*/


    public List<OpportunityByProductDTO> reportOpportunityByProduct() {

        List<OpportunityByProductDTO> result = accountClient.reportOpportunityByProduct();

        return result;
    }

    public List<OpportunityByProductDTO> reportOpportunityClosedWonByProduct() {

        List<OpportunityByProductDTO> result = accountClient.reportOpportunityClosedWonByProduct();

        return result;
    }

    public List<OpportunityByProductDTO> reportOpportunityClosedLostByProduct() {

        List<OpportunityByProductDTO> result = accountClient.reportOpportunityClosedLostByProduct();

        return result;
    }

    public List<OpportunityByProductDTO> reportOpportunityOpenByProduct() {

        List<OpportunityByProductDTO> result = accountClient.reportOpportunityOpenByProduct();

        return result;
    }

    /* ---------------------------------- BY COUNTRY -------------------------------------*/


    public List<OpportunitiesByCountryDTO> reportOpportunityByCountry() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");
        List<OpportunitiesByCountryDTO> result = circuitBreaker.run(()->accountClient.reportOpportunityByCountry(),
                throwable -> opportunityByCountryFallback());
        return result;
    }


    public List<OpportunitiesByCountryDTO> reportOpportunityClosedWonByCountry() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");
        List<OpportunitiesByCountryDTO> result = circuitBreaker.run(()->accountClient.reportOpportunityClosedWonByCountry(),
                throwable -> opportunityByCountryFallback());
        return result;
    }

    public List<OpportunitiesByCountryDTO> reportOpportunityClosedLostByCountry() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");
        List<OpportunitiesByCountryDTO> result = circuitBreaker.run(()->accountClient.reportOpportunityClosedLostByCountry(),
                throwable -> opportunityByCountryFallback());
        return result;
    }

    public List<OpportunitiesByCountryDTO> reportOpportunityOpenByCountry() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");
        List<OpportunitiesByCountryDTO> result = circuitBreaker.run(()->accountClient.reportOpportunityOpenByCountry(),
                throwable -> opportunityByCountryFallback());
        return result;
    }


    /* ---------------------------------- BY CITY -------------------------------------*/
    public List<OpportunitiesByCityDTO> reportOpportunityByCity() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");
        List<OpportunitiesByCityDTO> result = circuitBreaker.run(()->accountClient.reportOpportunityByCity(),
                throwable -> opportunityByCityFallback());
        return result;
    }


    public List<OpportunitiesByCityDTO> reportOpportunityClosedWonByCity() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");
        List<OpportunitiesByCityDTO> result = circuitBreaker.run(()->accountClient.reportOpportunityClosedWonByCity(),
                throwable -> opportunityByCityFallback());
        return result;
    }

    public List<OpportunitiesByCityDTO> reportOpportunityClosedLostByCity() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");
        List<OpportunitiesByCityDTO> result = circuitBreaker.run(()->accountClient.reportOpportunityClosedLostByCity(),
                throwable -> opportunityByCityFallback());
        return result;    }

    public List<OpportunitiesByCityDTO> reportOpportunityOpenByCity() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");
        List<OpportunitiesByCityDTO> result = circuitBreaker.run(()->accountClient.reportOpportunityOpenByCity(),
                throwable -> opportunityByCityFallback());
        return result;    }



    /* ---------------------------------- BY INDUSTRY -------------------------------------*/
    public List<OpportunitiesByIndustryDTO> reportOpportunityByIndustry() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");
        List<OpportunitiesByIndustryDTO> result = circuitBreaker.run(()->accountClient.reportOpportunityByIndustry(),
                throwable -> opportunityByIndustryFallback());
        return result;
    }



    public List<OpportunitiesByIndustryDTO> reportOpportunityClosedWonByIndustry() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");
        List<OpportunitiesByIndustryDTO> result = circuitBreaker.run(()->accountClient.reportOpportunityClosedWonByIndustry(),
                throwable -> opportunityByIndustryFallback());
        return result;
    }


    public List<OpportunitiesByIndustryDTO> reportOpportunityClosedLostByIndustry() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");
        List<OpportunitiesByIndustryDTO> result = circuitBreaker.run(()->accountClient.reportOpportunityClosedLostByIndustry(),
                throwable -> opportunityByIndustryFallback());
        return result;
    }

    public List<OpportunitiesByIndustryDTO> reportOpportunityOpenByIndustry() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");
        List<OpportunitiesByIndustryDTO> result = circuitBreaker.run(()->accountClient.reportOpportunityOpenByIndustry(),
                throwable -> opportunityByIndustryFallback());
        return result;
    }



    /* ---------------------------------- EMPLOYEE STATS -------------------------------------*/
    public Integer reportMaxEmployeeCount() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");
        Integer maxEmployeeCount = circuitBreaker.run(() -> accountClient.getMaxEmployeeCount(),
                throwable -> getMaxEmployeeCountFallback());
        return maxEmployeeCount;
    }

    public Integer reportMinEmployeeCount() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");
        Integer minEmployeeCount = circuitBreaker.run(() -> accountClient.getMinEmployeeCount(),
                throwable -> getMinEmployeeCountFallback());
        return minEmployeeCount;
    }

    public double reportMeanEmployeeCount() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");
        double meanEmployeeCount = circuitBreaker.run(() -> accountClient.getMeanEmployeeCount(),
                throwable -> getMeanEmployeeCountFallback());
        return meanEmployeeCount;
    }

    public double reportMedianEmployeeCount() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");
        double medianEmployeeCount = circuitBreaker.run(() -> accountClient.getMedianEmployeeCount(),
                throwable -> getMedianEmployeeCountFallback());
        return medianEmployeeCount;
    }



    /* --------------------------- OPPORTUNITY PER ACCOUNT STATS -------------------------------------*/
    public Integer reportMaxOpportunitiesPerAccount() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");
        Integer maxOppos = circuitBreaker.run(() -> accountClient.getMaxOpportunitiesPerAccount(),
                throwable -> getMaxOpportunitiesPerAccountFallback());
        return maxOppos;
    }

    public Integer reportMinOpportunitiesPerAccount() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");
        Integer minOppos = circuitBreaker.run(() -> accountClient.getMinOpportunitiesPerAccount(),
                throwable -> getMinOpportunitiesPerAccountFallback());
        return minOppos;
    }

    public double reportMeanOpportunitiesPerAccount() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");
        double meanOppos = circuitBreaker.run(() -> accountClient.getMeanOpportunitiesPerAccount(),
                throwable -> getMeanOpportunitiesPerAccountFallback());
        return meanOppos;
    }

    public double reportMedianOpportunitiesPerAccount() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");
        double medianOppos = circuitBreaker.run(() -> accountClient.getMedianOpportunitiesPerAccount(),
                throwable -> getMedianOpportunitiesPerAccountFallback());
        return medianOppos;
    }



    /* --------------------------- QUANTITY STATS -------------------------------------*/
    public Integer reportMaxQuantity() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");
        Integer maxQuantity = circuitBreaker.run(() -> accountClient.getMaxQuantity(),
                throwable -> getMaxQuantityFallback());
        return maxQuantity;
    }

    public Integer reportMinQuantity() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");
        Integer minQuantity = circuitBreaker.run(() -> accountClient.getMinQuantity(),
                throwable -> getMinQuantityFallback());
        return minQuantity;
    }

    public double reportMeanQuantity() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");
        double meanQuantity = circuitBreaker.run(() -> accountClient.getMeanQuantity(),
                throwable -> getMeanQuantityFallback());
        return meanQuantity;
    }

    public double reportMedianQuantity() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");
        double medianQuantity = circuitBreaker.run(() -> accountClient.getMedianQuantity(),
                throwable -> getMedianQuantityFallback());
        return medianQuantity;
    }

    /* --------------------------------- PDF GENERATOR ----------------------------*/

    public void generatePdfReports() {
        HashMap<String, List<OpportunitiesByCityDTO>> hashMap1 = new HashMap<>();
        HashMap<String, List<OpportunitiesByCountryDTO>> hashMap2 = new HashMap<>();
        HashMap<String, List<OpportunitiesByIndustryDTO>> hashMap3 = new HashMap<>();
        HashMap<String, List<OpportunityByProductDTO>> hashMap4 = new HashMap<>();
        HashMap<String, List<ReportDTO>> hashMap5 = new HashMap<>();
        HashMap<String, Double> stats = new HashMap<>();

        hashMap5.put("Count of Opportunities By Sales Rep",  reportOpportunityBySalesRep());
        hashMap5.put("Count of Opportunities by Sales Reps Where Closed Won", reportOpportunityClosedWonBySalesRep());
        hashMap5.put("Count of Opportunities by Sales Reps Where Closed Lost", reportOpportunityClosedLostBySalesRep());
        hashMap5.put("Count of Opportunities by Sales Reps Where Closed Open", reportOpportunityOpenBySalesRep());
        hashMap5.put("Count of Leads by Sales Reps", reportLeadBySalesRep());
        hashMap4.put("Count of Opportunities by Product", reportOpportunityByProduct());
        hashMap4.put("Count of Opportunities by Product Where Closed Won", reportOpportunityClosedWonByProduct());
        hashMap4.put("Count of Opportunities by Product Where Closed Lost", reportOpportunityClosedLostByProduct());
        hashMap4.put("Count of Opportunities by Product Where Open", reportOpportunityOpenByProduct());
        hashMap2.put("Count of Opportunities by Country", reportOpportunityByCountry());
        hashMap2.put("Count of Opportunities by Country Where Closed Won", reportOpportunityClosedWonByCountry());
        hashMap2.put("Count of Opportunities by Country Where Closed Lost", reportOpportunityClosedLostByCountry());
        hashMap2.put("Count of Opportunities by Country Where Open", reportOpportunityOpenByCountry());
        hashMap1.put("Count of Opportunities by City", reportOpportunityByCity());
        hashMap1.put("Count of Opportunities by City Where Closed Won", reportOpportunityClosedWonByCity());
        hashMap1.put("Count of Opportunities by City Where Closed Lost", reportOpportunityClosedLostByCity());
        hashMap1.put("Count of Opportunities by City Where Open", reportOpportunityOpenByCity());
        hashMap3.put("Count of Opportunities by Industry", reportOpportunityByIndustry());
        hashMap3.put("Count of Opportunities by Industry Where Closed Won", reportOpportunityClosedWonByIndustry());
        hashMap3.put("Count of Opportunities by Industry Where Closed Lost", reportOpportunityClosedLostByIndustry());
        hashMap3.put("Count of Opportunities by Industry Where Open", reportOpportunityOpenByIndustry());


        stats.put("Mean of Quantity", reportMeanQuantity());
        stats.put("Max of Quantity", reportMaxQuantity().doubleValue());
        stats.put("Min of Quantity", reportMinQuantity().doubleValue());
        stats.put("Median of Quantity", reportMedianQuantity());
        stats.put("Mean of Employee Count", reportMeanEmployeeCount());
        stats.put("Max of Employee Count", reportMaxEmployeeCount().doubleValue());
        stats.put("Min of Employee Count", reportMinEmployeeCount().doubleValue());
        stats.put("Median of Employee Count", reportMedianEmployeeCount());
        stats.put("Mean of Opportunities in Accounts", reportMeanOpportunitiesPerAccount());
        stats.put("Max  of opportunities in Accounts", reportMaxOpportunitiesPerAccount().doubleValue());
        stats.put("Min of Opportunities in Accounts", reportMinOpportunitiesPerAccount().doubleValue());
        stats.put("Median of Opportunities in Accounts", reportMedianOpportunitiesPerAccount());


        PdfGenerator.init(hashMap1, hashMap2, hashMap3, hashMap4, hashMap5, stats);
        try {
            PdfGenerator.generatePdf();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* --------------------------------- UTILITY METHODS ----------------------------*/

    private List<ReportDTO> getReportDTOS(List<OpportunityBySalesRepDTO> result) {
        List<ReportDTO> resultWithNames = new ArrayList<>();
        for (OpportunityBySalesRepDTO opportunityBySalesRepDTO : result) {
            SalesRepDTO salesRepDTO = getSalesRepById(opportunityBySalesRepDTO.getSalesRepId());
            ReportDTO reportDTO = new ReportDTO();
            reportDTO.setName(salesRepDTO.getName());
            reportDTO.setCount(opportunityBySalesRepDTO.getCount());
            resultWithNames.add(reportDTO);
        }
        return resultWithNames;
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

    private Integer getMaxEmployeeCountFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Account service not available");
    }

    private Integer getMinEmployeeCountFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Account service not available");
    }

    private double getMeanEmployeeCountFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Account service not available");
    }

    private double getMedianEmployeeCountFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Account service not available");
    }

    private Integer getMaxOpportunitiesPerAccountFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Account service not available");
    }

    private Integer getMinOpportunitiesPerAccountFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Account service not available");
    }

    private double getMeanOpportunitiesPerAccountFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Account service not available");
    }

    private double getMedianOpportunitiesPerAccountFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Account service not available");
    }

    private Integer getMaxQuantityFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Account service not available");
    }

    private Integer getMinQuantityFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Account service not available");
    }

    private double getMeanQuantityFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Account service not available");
    }

    private double getMedianQuantityFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Account service not available");
    }

    private List<OpportunitiesByCityDTO> opportunityByCityFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Account service not available");
    }

    private List<OpportunitiesByIndustryDTO> opportunityByIndustryFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Account service not available");
    }

    private List<OpportunitiesByCountryDTO> opportunityByCountryFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Account service not available");
    }
}
