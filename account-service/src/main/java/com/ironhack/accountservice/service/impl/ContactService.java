package com.ironhack.accountservice.service.impl;

import com.ironhack.accountservice.controller.client.*;
import com.ironhack.accountservice.controller.dtos.*;
import com.ironhack.accountservice.model.*;
import com.ironhack.accountservice.repository.*;
import com.ironhack.accountservice.service.interfaces.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.server.*;

@Service
public class ContactService implements IContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private LeadClient leadClient;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public ContactDTO getContact(Integer id) {
        if(contactRepository.existsById(id)) {

            Contact contact = contactRepository.findById(id).get();

            AccountDTO accountDTO = accountService.getAccount(contact.getAccount().getId());

            return new ContactDTO(contact.getId(), contact.getName(), contact.getEmail(),
                    contact.getCompanyName(), contact.getPhoneNumber(), accountDTO);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found");
        }
    }

    @Override
    public ContactDTO postContact(LeadDTO leadDTO, AccountDTO accountDTO) {
        Account account = accountRepository.findById(accountDTO.getId()).get();

        Contact contact = new Contact(leadDTO.getName(), leadDTO.getEmail(),
                leadDTO.getCompanyName(), leadDTO.getPhoneNumber(), account);

        contact = contactRepository.save(contact);

        ContactDTO contactDTO=new ContactDTO(contact.getId(), contact.getName(), contact.getEmail(),
                contact.getCompanyName(), contact.getPhoneNumber(), accountDTO);

        return contactDTO;
    }
}
