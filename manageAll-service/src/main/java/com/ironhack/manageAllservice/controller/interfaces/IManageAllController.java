package com.ironhack.manageAllservice.controller.interfaces;

import com.ironhack.manageAllservice.controller.dtos.LeadDTO;
import com.ironhack.manageAllservice.controller.dtos.OpportunityDTO;
import com.ironhack.manageAllservice.controller.dtos.SalesRepDTO;

public interface IManageAllController {

    SalesRepDTO newSalesRep(SalesRepDTO salesRepDTO);

    LeadDTO newLead(LeadDTO leadDTO);

    OpportunityDTO closeOpportunity(Integer id, String status);
}
