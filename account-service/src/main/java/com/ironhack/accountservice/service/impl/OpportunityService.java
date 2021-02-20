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
    IContactService contactService;

    @Autowired
    OpportunityRepository opportunityRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    LeadClient leadClient;

    @Override
    public OpportunityDTO getOpportunity(Integer id) {
        if(opportunityRepository.existsById(id)) {

            Opportunity opportunity = opportunityRepository.findById(id).get();

            return new OpportunityDTO(opportunity.getId(),opportunity.getProduct(),  opportunity.getQuantity(), opportunity.getDecisionMaker(),
                    opportunity.getStatus(), opportunity.getSalesRep(), opportunity.getAccount());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Opportunity not found");
        }
    }

    @Override
    public OpportunityDTO postOpportunity(Integer leadId, Integer accountId, PurchaseDTO purchaseDTO) {

        LeadDTO leadDTO= leadClient.getById(leadId);

        Optional<Account> account=accountRepository.findById(accountId);

        Contact contact=contactService.postContact(leadDTO, account.get());

        Opportunity opportunity=opportunityRepository.save(new Opportunity(purchaseDTO.getProduct(), purchaseDTO.getQuantity(),
                contact, Status.OPEN,account.get(), leadDTO.getSalesRepId()));

        OpportunityDTO opportunityDTO=new OpportunityDTO(opportunity.getId(), opportunity.getProduct(), opportunity.getQuantity(),
               opportunity.getDecisionMaker(), opportunity.getStatus(),opportunity.getSalesRep(),account.get());

        return opportunityDTO;
    }
}
