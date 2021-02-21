package com.ironhack.leadservice.controller.impl;

import com.ironhack.leadservice.controller.dto.LeadDTO;
import com.ironhack.leadservice.controller.interfaces.ILeadController;
import com.ironhack.leadservice.service.interfaces.ILeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LeadController implements ILeadController {

    @Autowired
    private ILeadService service;

    @GetMapping("/lead")
    @ResponseStatus(HttpStatus.OK)
    public List<LeadDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/lead/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LeadDTO getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping("/lead")
    @ResponseStatus(HttpStatus.CREATED)
    public LeadDTO add(@RequestBody LeadDTO leadDTO) {
        return service.add(leadDTO);
    }

    @DeleteMapping("/lead/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public LeadDTO delete(@PathVariable Integer id) {
        return service.delete(id);
    }
}
