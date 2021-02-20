package com.ironhack.accountservice.controller.interfaces;

import com.ironhack.accountservice.controller.dtos.OpportunityDTO;
import com.ironhack.accountservice.controller.dtos.PurchaseDTO;
import com.ironhack.accountservice.enums.Status;

public interface IOpportunityController {

    OpportunityDTO getOpportunity(Integer id);

    OpportunityDTO postOpportunity(Integer leadId, Integer accountId, PurchaseDTO purchaseDTO);

    OpportunityDTO closeOpportunity(Integer id, String status);
}
