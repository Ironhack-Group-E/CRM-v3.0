package com.ironhack.leadservice.controller.interfaces;

import com.ironhack.leadservice.controller.dto.LeadDTO;

import java.util.List;

public interface ILeadController {
    List<LeadDTO> getAll();
    LeadDTO getById(Integer id);
    LeadDTO add(LeadDTO leadDTO);
    LeadDTO delete(Integer id);
}
