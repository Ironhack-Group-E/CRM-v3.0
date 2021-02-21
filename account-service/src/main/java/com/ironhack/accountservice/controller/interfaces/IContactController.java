package com.ironhack.accountservice.controller.interfaces;

import com.ironhack.accountservice.controller.dtos.ContactDTO;

import java.util.List;

import com.ironhack.accountservice.controller.dtos.ContactDTO;

public interface IContactController {

    ContactDTO getContact(Integer id);

    List<ContactDTO> getAllContact();
}
