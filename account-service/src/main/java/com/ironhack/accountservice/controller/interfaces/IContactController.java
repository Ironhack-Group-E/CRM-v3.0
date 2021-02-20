package com.ironhack.accountservice.controller.interfaces;

import com.ironhack.accountservice.controller.dtos.ContactDTO;

public interface IContactController {

    ContactDTO getContact(Integer id);
}
