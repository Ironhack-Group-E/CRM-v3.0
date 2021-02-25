package com.ironhack.accountservice.service.impl;

import com.ironhack.accountservice.controller.client.LeadClient;
import com.ironhack.accountservice.controller.dtos.*;
import com.ironhack.accountservice.controller.dtos.report.*;
import com.ironhack.accountservice.enums.*;
import com.ironhack.accountservice.model.Account;
import com.ironhack.accountservice.model.Contact;
import com.ironhack.accountservice.model.Opportunity;
import com.ironhack.accountservice.repository.AccountRepository;
import com.ironhack.accountservice.repository.ContactRepository;
import com.ironhack.accountservice.repository.OpportunityRepository;
import com.ironhack.accountservice.service.interfaces.IAccountService;
import com.ironhack.accountservice.service.interfaces.IContactService;
import com.ironhack.accountservice.service.interfaces.IOpportunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpportunityService implements IOpportunityService {

    @Autowired
    private IContactService contactService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private LeadClient leadClient;

    @Override
    public OpportunityDTO getOpportunity(Integer id) {
        if(opportunityRepository.existsById(id)) {

            Opportunity opportunity = opportunityRepository.findById(id).get();

            AccountDTO accountDTO = accountService.getAccount(opportunity.getAccount().getId());

            ContactDTO contactDTO = contactService.getContact(opportunity.getDecisionMaker().getId());

            return new OpportunityDTO(opportunity.getId(),opportunity.getProduct(),  opportunity.getQuantity(), contactDTO,
                    opportunity.getStatus(), opportunity.getSalesRep(), accountDTO);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Opportunity not found");
        }
    }

    @Override
    public List<OpportunityDTO> getAllOpportunity() {
        List<Opportunity> opportunityList = opportunityRepository.findAll();

        if (opportunityList.size() == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There are no opportunities created");
        }

        List<OpportunityDTO> opportunityDTOList = new ArrayList<>();

        for(Opportunity eachOpportunity:opportunityList){
            opportunityDTOList.add(new OpportunityDTO(eachOpportunity.getId(),
                    eachOpportunity.getProduct(),
                    eachOpportunity.getQuantity(),
                    contactService.getContact(eachOpportunity.getDecisionMaker().getId()),
                    eachOpportunity.getStatus(),
                    eachOpportunity.getSalesRep(),
                    accountService.getAccount(eachOpportunity.getAccount().getId())));
        }
        return opportunityDTOList;
    }

    @Override
    public OpportunityDTO postOpportunity(Integer leadId, Integer accountId, PurchaseDTO purchaseDTO) {

        LeadDTO leadDTO = leadClient.getById(leadId);

        Account account = accountRepository.findById(accountId).get();

        AccountDTO accountDTO = new AccountDTO(account.getId(), account.getCompanyName(), account.getIndustry(),
                account.getEmployeeCount(), account.getCity(), account.getCountry());

        ContactDTO contactDTO = contactService.postContact(leadDTO, accountDTO);

        Contact contact = contactRepository.findById(contactDTO.getId()).get();

        Opportunity opportunity = opportunityRepository.save(new Opportunity(purchaseDTO.getProduct(), purchaseDTO.getQuantity(),
                contact, Status.OPEN, account, leadDTO.getSalesRepId()));

        OpportunityDTO opportunityDTO = new OpportunityDTO(opportunity.getId(), opportunity.getProduct(), opportunity.getQuantity(),
               contactDTO, opportunity.getStatus(), opportunity.getSalesRep(), accountDTO);

        return opportunityDTO;
    }

    public OpportunityDTO closeOpportunity(Integer id, String status) {

        if(!opportunityRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Opportunity not found");
        }

        status = status.toUpperCase();

        if(!status.equals("CLOSED_WON") && !status.equals("CLOSED_LOST")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status not value, must be 'CLOSED_WON' or 'CLOSED_LOST' (case insensitive)");
        }

        Opportunity opportunity = opportunityRepository.findById(id).get();

        opportunity.setStatus(Status.valueOf(status.toUpperCase()));

        opportunity = opportunityRepository.save(opportunity);

        AccountDTO accountDTO = accountService.getAccount(opportunity.getAccount().getId());

        ContactDTO contactDTO = contactService.getContact(opportunity.getDecisionMaker().getId());

        return new OpportunityDTO(opportunity.getId(), opportunity.getProduct(), opportunity.getQuantity(),
                contactDTO, opportunity.getStatus(), opportunity.getSalesRep(), accountDTO);
    }

    public List<OpportunityBySalesRepDTO> reportOpportunityBySalesRep() {

        List<Object[]> report = opportunityRepository.countOfOpportunitiesBySalesRepId();

        List<OpportunityBySalesRepDTO> result = getOpportunityBySalesRepDTOS(report);

        return result;
    }


    public List<OpportunityBySalesRepDTO> reportOpportunityClosedWonBySalesRep() {

        List<Object[]> report = opportunityRepository.countOfOpportunitiesBySalesRepsWhereClosedWon();

        List<OpportunityBySalesRepDTO> result = getOpportunityBySalesRepDTOS(report);

        return result;
    }

    public List<OpportunityBySalesRepDTO> reportOpportunityClosedLostBySalesRep() {

        List<Object[]> report = opportunityRepository.countOfOpportunitiesBySalesRepsWhereClosedLost();

        List<OpportunityBySalesRepDTO> result = getOpportunityBySalesRepDTOS(report);

        return result;
    }

    public List<OpportunityBySalesRepDTO> reportOpportunityOpenBySalesRep() {

        List<Object[]> report = opportunityRepository.countOfOpportunitiesBySalesRepsWhereOpen();

        List<OpportunityBySalesRepDTO> result = getOpportunityBySalesRepDTOS(report);

        return result;
    }

    private List<OpportunityBySalesRepDTO> getOpportunityBySalesRepDTOS(List<Object[]> report) {
        List<OpportunityBySalesRepDTO> result = new ArrayList<>();
        for (Object[] objects : report) {
            OpportunityBySalesRepDTO opportunityBySalesRepDTO = new OpportunityBySalesRepDTO((Integer) objects[0], (Long) objects[1]);
            result.add(opportunityBySalesRepDTO);
        }
        return result;
    }


    public Integer getMaxQuantity() {
        Integer maxQuantity = opportunityRepository.maxOfQuantity();
        if (maxQuantity == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There are no trucks ordered");
        } else return maxQuantity;
    }

    public Integer getMinQuantity() {
        Integer minQuantity = opportunityRepository.minOfQuantity();
        if (minQuantity == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There are no trucks ordered");
        } else return minQuantity;
    }

    public double getAverageQuantity() {
        double avgQuantity = opportunityRepository.meanOfQuantity();
        if (avgQuantity == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There are no trucks ordered");
        } else return avgQuantity;
    }

    public double getMedianQuantity() {
        double medianQuantity = opportunityRepository.medianOfQuantity();
        if (medianQuantity == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There are no trucks ordered");
        } else return medianQuantity;
    }


    public List<OpportunityByProductDTO> reportOpportunityByProduct() {
        List<Object[]> report = opportunityRepository.countOfOpportunitiesByProduct();

        List<OpportunityByProductDTO> result = new ArrayList<>();
        for (Object[] objects : report) {
            OpportunityByProductDTO opportunityByProductDTO = new OpportunityByProductDTO( objects[0].toString(), (Long) objects[1]);
            result.add(opportunityByProductDTO);
        }

        return result;
    }


    public List<OpportunityByProductDTO> reportOpportunityClosedWonByProduct() {
        List<Object[]> report = opportunityRepository.countOfOpportunitiesByProductWhereClosedWon();

        List<OpportunityByProductDTO> result = new ArrayList<>();
        for (Object[] objects : report) {
            OpportunityByProductDTO opportunityByProductDTO = new OpportunityByProductDTO( objects[0].toString(), (Long) objects[1]);
            result.add(opportunityByProductDTO);
        }

        return result;
    }


    public List<OpportunityByProductDTO> reportOpportunityClosedLostByProduct() {
        List<Object[]> report = opportunityRepository.countOfOpportunitiesByProductWhereClosedLost();

        List<OpportunityByProductDTO> result = new ArrayList<>();
        for (Object[] objects : report) {
            OpportunityByProductDTO opportunityByProductDTO = new OpportunityByProductDTO( objects[0].toString(), (Long) objects[1]);
            result.add(opportunityByProductDTO);
        }

        return result;
    }


    public List<OpportunityByProductDTO> reportOpportunityOpenByProduct() {
        List<Object[]> report = opportunityRepository.countOfOpportunitiesByProductWhereOpen();

        List<OpportunityByProductDTO> result = new ArrayList<>();
        for (Object[] objects : report) {
            OpportunityByProductDTO opportunityByProductDTO = new OpportunityByProductDTO(objects[0].toString(), (Long) objects[1]);
            result.add(opportunityByProductDTO);
        }

        return result;
    }


    public List<OpportunitiesByCountryDTO> reportOpportunityByCountry() {
        List<Object[]> report = opportunityRepository.countOfOpportunitiesByCountry();

        List<OpportunitiesByCountryDTO> result = new ArrayList<>();
        for (Object[] objects : report) {
            OpportunitiesByCountryDTO opportunityByCountryDTO = new OpportunitiesByCountryDTO( (String) objects[0], (Long) objects[1]);
            result.add(opportunityByCountryDTO);
        }

        return result;
    }


    public List<OpportunitiesByCountryDTO> reportOpportunityClosedWonByCountry() {
        List<Object[]> report = opportunityRepository.countOfOpportunitiesByCountryWhereClosedWon();

        List<OpportunitiesByCountryDTO> result = new ArrayList<>();
        for (Object[] objects : report) {
            OpportunitiesByCountryDTO opportunityByCountryDTO = new OpportunitiesByCountryDTO( (String) objects[0], (Long) objects[1]);
            result.add(opportunityByCountryDTO);
        }

        return result;
    }


    public List<OpportunitiesByCountryDTO> reportOpportunityClosedLostByCountry() {
        List<Object[]> report = opportunityRepository.countOfOpportunitiesByCountryWhereClosedLost();

        List<OpportunitiesByCountryDTO> result = new ArrayList<>();
        for (Object[] objects : report) {
            OpportunitiesByCountryDTO opportunityByCountryDTO = new OpportunitiesByCountryDTO( (String) objects[0], (Long) objects[1]);
            result.add(opportunityByCountryDTO);
        }

        return result;
    }


    public List<OpportunitiesByCountryDTO> reportOpportunityOpenByCountry() {
        List<Object[]> report = opportunityRepository.countOfOpportunitiesByCountryWhereOpen();

        List<OpportunitiesByCountryDTO> result = new ArrayList<>();
        for (Object[] objects : report) {
            OpportunitiesByCountryDTO opportunityByCountryDTO = new OpportunitiesByCountryDTO( (String) objects[0], (Long) objects[1]);
            result.add(opportunityByCountryDTO);
        }

        return result;
    }

    public List<OpportunitiesByCityDTO> reportOpportunityByCity() {
        List<Object[]> report = opportunityRepository.countOfOpportunitiesByCity();
        List<OpportunitiesByCityDTO> result = new ArrayList<>();
        for(Object[] objects:report) {
            OpportunitiesByCityDTO opportunitiesByCityDTO = new OpportunitiesByCityDTO((String) objects[0], (Long) objects[1]);
            result.add(opportunitiesByCityDTO);
        }
        return result;
    }

    public List<OpportunitiesByCityDTO> reportOpportunityClosedWonByCity() {
        List<Object[]> report = opportunityRepository.countOfOpportunitiesByCityWhereClosedWon();
        List<OpportunitiesByCityDTO> result = new ArrayList<>();
        for(Object[] objects:report) {
            OpportunitiesByCityDTO opportunitiesByCityDTO = new OpportunitiesByCityDTO((String) objects[0], (Long) objects[1]);
            result.add(opportunitiesByCityDTO);
        }
        return result;
    }

    public List<OpportunitiesByCityDTO> reportOpportunityClosedLostByCity() {
        List<Object[]> report = opportunityRepository.countOfOpportunitiesByCityWhereClosedLost();
        List<OpportunitiesByCityDTO> result = new ArrayList<>();
        for(Object[] objects:report) {
            OpportunitiesByCityDTO opportunitiesByCityDTO = new OpportunitiesByCityDTO((String) objects[0], (Long) objects[1]);
            result.add(opportunitiesByCityDTO);
        }
        return result;
    }

    public List<OpportunitiesByCityDTO> reportOpportunityOpenByCity() {
        List<Object[]> report = opportunityRepository.countOfOpportunitiesByCityWhereOpen();
        List<OpportunitiesByCityDTO> result = new ArrayList<>();
        for(Object[] objects:report) {
            OpportunitiesByCityDTO opportunitiesByCityDTO = new OpportunitiesByCityDTO((String) objects[0], (Long) objects[1]);
            result.add(opportunitiesByCityDTO);
        }
        return result;
    }

    public List<OpportunitiesByIndustryDTO> reportOpportunityByIndustry() {
        List<Object[]> report = opportunityRepository.countOfOpportunitiesByIndustry();
        List<OpportunitiesByIndustryDTO> result = new ArrayList<>();
        for(Object[] objects:report) {
            OpportunitiesByIndustryDTO opportunitiesByIndustryDTO = new OpportunitiesByIndustryDTO(objects[0].toString(), (Long) objects[1]);
            result.add(opportunitiesByIndustryDTO);
        }
        return result;
    }

    public List<OpportunitiesByIndustryDTO> reportOpportunityClosedLostByIndustry() {
        List<Object[]> report = opportunityRepository.countOfOpportunitiesByIndustryWhereClosedLost();
        List<OpportunitiesByIndustryDTO> result = new ArrayList<>();
        for(Object[] objects:report) {
            OpportunitiesByIndustryDTO opportunitiesByIndustryDTO = new OpportunitiesByIndustryDTO(objects[0].toString(), (Long) objects[1]);
            result.add(opportunitiesByIndustryDTO);
        }
        return result;
    }

    public List<OpportunitiesByIndustryDTO> reportOpportunityClosedWonByIndustry() {
        List<Object[]> report = opportunityRepository.countOfOpportunitiesByIndustryWhereClosedWon();
        List<OpportunitiesByIndustryDTO> result = new ArrayList<>();
        for(Object[] objects:report) {
            OpportunitiesByIndustryDTO opportunitiesByIndustryDTO = new OpportunitiesByIndustryDTO(objects[0].toString(), (Long) objects[1]);
            result.add(opportunitiesByIndustryDTO);
        }
        return result;
    }

    public List<OpportunitiesByIndustryDTO> reportOpportunityOpenByIndustry() {
        List<Object[]> report = opportunityRepository.countOfOpportunitiesByIndustryWhereOpen();
        List<OpportunitiesByIndustryDTO> result = new ArrayList<>();
        for(Object[] objects:report) {
            OpportunitiesByIndustryDTO opportunitiesByIndustryDTO = new OpportunitiesByIndustryDTO(objects[0].toString(), (Long) objects[1]);
            result.add(opportunitiesByIndustryDTO);
        }
        return result;
    }


}
