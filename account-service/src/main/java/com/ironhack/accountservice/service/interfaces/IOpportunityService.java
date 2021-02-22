package com.ironhack.accountservice.service.interfaces;

import com.ironhack.accountservice.controller.dtos.*;
import com.ironhack.accountservice.controller.dtos.report.OpportunityBySalesRepDTO;
import com.ironhack.accountservice.model.*;

import java.util.List;

public interface IOpportunityService {

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

    double getAverageQuantity();

    double getMedianQuantity();
}
