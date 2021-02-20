package com.ironhack.accountservice.service.impl;

import com.ironhack.accountservice.controller.client.*;
import com.ironhack.accountservice.controller.dtos.*;
import com.ironhack.accountservice.enums.*;
import com.ironhack.accountservice.model.*;
import com.ironhack.accountservice.repository.*;
import com.ironhack.accountservice.service.interfaces.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.server.*;

import java.util.*;

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
}
