package com.ironhack.leadservice.service.interfaces;

import com.ironhack.leadservice.controller.dto.LeadDTO;

import java.util.List;

public interface ILeadService {
    List<LeadDTO> getAll();
    LeadDTO getById(Integer id);
    LeadDTO add(LeadDTO leadDTO);
    LeadDTO delete(Integer id);
}
