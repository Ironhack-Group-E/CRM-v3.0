package com.ironhack.accountservice.service.interfaces;

import com.ironhack.accountservice.controller.dtos.*;
import com.ironhack.accountservice.model.*;

public interface IContactService {
    ContactDTO getContact(Integer id);

    Contact postContact(LeadDTO leadDTO, Account account);
}
