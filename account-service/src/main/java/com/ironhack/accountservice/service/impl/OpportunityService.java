package com.ironhack.accountservice.service.impl;

import com.ironhack.accountservice.controller.client.LeadClient;
import com.ironhack.accountservice.controller.dtos.*;
import com.ironhack.accountservice.enums.Status;
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

    public OpportunityDTO closeOpportunity(Integer id, String status) {

        if(!opportunityRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Opportunity not found");
        }

        status = status.toUpperCase();

        if(!status.equals("CLOSE_WON") && !status.equals("CLOSE_LOST")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status not value, must be 'CLOSE_WON' or 'CLOSE_LOST' (case insensitive)");
        }

        Opportunity opportunity = opportunityRepository.findById(id).get();

        opportunity.setStatus(Status.valueOf(status.toUpperCase()));

        opportunity = opportunityRepository.save(opportunity);

        AccountDTO accountDTO = accountService.getAccount(opportunity.getAccount().getId());

        ContactDTO contactDTO = contactService.getContact(opportunity.getDecisionMaker().getId());

        return new OpportunityDTO(opportunity.getId(), opportunity.getProduct(), opportunity.getQuantity(),
                contactDTO, opportunity.getStatus(), opportunity.getSalesRep(), accountDTO);
    }


}
