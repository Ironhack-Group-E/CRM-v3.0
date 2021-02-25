package com.ironhack.accountservice.controller.interfaces;

import com.ironhack.accountservice.controller.dtos.OpportunityDTO;
import com.ironhack.accountservice.controller.dtos.PurchaseDTO;
import com.ironhack.accountservice.controller.dtos.report.OpportunityBySalesRepDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IOpportunityController {

     OpportunityDTO getOpportunity(Integer id);

     List<OpportunityDTO> getAllOpportunity();

     OpportunityDTO postOpportunity(Integer leadId, Integer accountId, PurchaseDTO purchaseDTO);

     OpportunityDTO closeOpportunity(Integer id, String status);

     List<OpportunityBySalesRepDTO> reportOpportunityBySalesRep();

     List<OpportunityBySalesRepDTO> reportOpportunityClosedWonBySalesRep();

     List<OpportunityBySalesRepDTO> reportOpportunityClosedLostBySalesRep();

     List<OpportunityBySalesRepDTO> reportOpportunityOpenBySalesRep();

     Integer getMaxQuantity();

     Integer getMinQuantity();

     double getMeanQuantity();

     double getMedianQuantity();
}
