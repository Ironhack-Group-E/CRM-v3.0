package com.ironhack.manageAllservice.service.interfaces;

import com.ironhack.manageAllservice.controller.dtos.LeadDTO;
import com.ironhack.manageAllservice.controller.dtos.OpportunityDTO;
import com.ironhack.manageAllservice.controller.dtos.SalesRepDTO;

public interface IManageAllService {

    SalesRepDTO newSalesRep(SalesRepDTO salesRepDTO);

    LeadDTO newLead(LeadDTO leadDTO);

    OpportunityDTO closeOpportunity(Integer id, String status);
}
