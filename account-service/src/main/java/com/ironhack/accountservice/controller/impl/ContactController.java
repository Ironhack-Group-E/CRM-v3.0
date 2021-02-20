package com.ironhack.accountservice.controller.impl;

import com.ironhack.accountservice.controller.dtos.*;
import com.ironhack.accountservice.controller.interfaces.IContactController;
import com.ironhack.accountservice.service.impl.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

@RestController
public class ContactController implements IContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/contact/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContactDTO getContact(@PathVariable Integer id) {
        return contactService.getContact(id);
    }

}
