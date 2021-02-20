package com.ironhack.accountservice.service.interfaces;

import com.ironhack.accountservice.controller.dtos.*;

public interface IContactService {

    ContactDTO getContact(Integer id);

    ContactDTO postContact(LeadDTO leadDTO, AccountDTO accountDTO);
}
