package com.ironhack.accountservice.service.interfaces;

import com.ironhack.accountservice.controller.dtos.*;
import com.ironhack.accountservice.controller.dtos.report.*;

import java.util.*;

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

    List<OpportunityByProductDTO> reportOpportunityByProduct();

    List<OpportunityByProductDTO> reportOpportunityClosedWonByProduct();

    List<OpportunityByProductDTO> reportOpportunityClosedLostByProduct();

    List<OpportunityByProductDTO> reportOpportunityOpenByProduct();
}
