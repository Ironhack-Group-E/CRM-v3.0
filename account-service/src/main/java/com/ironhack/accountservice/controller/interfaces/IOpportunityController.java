package com.ironhack.accountservice.controller.interfaces;

import com.ironhack.accountservice.controller.dtos.OpportunityDTO;
import com.ironhack.accountservice.controller.dtos.PurchaseDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IOpportunityController {

     OpportunityDTO getOpportunity(Integer id);

     List<OpportunityDTO> getAllOpportunity();

     OpportunityDTO postOpportunity(Integer leadId, Integer accountId, PurchaseDTO purchaseDTO);

     OpportunityDTO closeOpportunity(Integer id, String status);
}
