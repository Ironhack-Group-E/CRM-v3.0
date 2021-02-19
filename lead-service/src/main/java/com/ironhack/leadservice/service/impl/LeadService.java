package com.ironhack.leadservice.service.impl;

import com.ironhack.leadservice.controller.dto.LeadDTO;
import com.ironhack.leadservice.model.Lead;
import com.ironhack.leadservice.repository.LeadRepository;
import com.ironhack.leadservice.service.interfaces.ILeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeadService implements ILeadService {

    @Autowired
    private LeadRepository repository;

    public List<LeadDTO> getAll() {
        List<LeadDTO> leadDTOS = new ArrayList<>();
        List<Lead> leads = repository.findAll();
        for(Lead lead : leads)
        {
            LeadDTO leadDTO = new LeadDTO();
            leadDTO.setId(lead.getId());
            leadDTO.setName(lead.getName());
            leadDTO.setEmail(lead.getEmail());
            leadDTO.setPhoneNumber(lead.getPhoneNumber());
            leadDTO.setCompanyName(lead.getCompanyName());
            leadDTO.setSalesRepId(lead.getSalesRepId());
            leadDTOS.add(leadDTO);
        }
        return leadDTOS;
    }

    public LeadDTO getById(Integer id) {
        if (!repository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Lead lead = repository.findById(id).get();
        LeadDTO leadDTO = new LeadDTO();
        leadDTO.setId(lead.getId());
        leadDTO.setName(lead.getName());
        leadDTO.setEmail(lead.getEmail());
        leadDTO.setPhoneNumber(lead.getPhoneNumber());
        leadDTO.setCompanyName(lead.getCompanyName());
        leadDTO.setSalesRepId(lead.getSalesRepId());

        return leadDTO;
    }

    public LeadDTO add(LeadDTO leadDTO) {
        Lead lead = new Lead();
        lead.setName(leadDTO.getName());
        lead.setEmail(leadDTO.getEmail());
        lead.setPhoneNumber(leadDTO.getPhoneNumber());
        lead.setSalesRepId(leadDTO.getSalesRepId());
        lead.setCompanyName(leadDTO.getCompanyName());
        lead = repository.save(lead);

        leadDTO.setId(lead.getId());
        return leadDTO;
    }
}
