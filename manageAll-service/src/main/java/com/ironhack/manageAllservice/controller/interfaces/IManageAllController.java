package com.ironhack.manageAllservice.controller.interfaces;

import com.ironhack.manageAllservice.controller.dtos.*;
import com.ironhack.manageAllservice.controller.dtos.report.ReportDTO;

import java.util.List;

public interface IManageAllController {

    SalesRepDTO newSalesRep(SalesRepDTO salesRepDTO);

    List<SalesRepDTO> showSalesRep();

    SalesRepDTO lookUpSalesRep(Integer id);

    LeadDTO newLead(LeadDTO leadDTO);

    List<LeadDTO> showLeads();

    LeadDTO lookUpLead(Integer id);

    OpportunityDTO convertLead(Integer id, Integer idAccount, PurchaseDTO purchaseDTO);

    OpportunityDTO convertLead(Integer id, PurchaseWithAccountDTO purchaseWithAccountDTO);

    List<OpportunityDTO> showOpportunity();

    OpportunityDTO lookUpOpportunity(Integer id);

    List<ContactDTO> showContact();

    ContactDTO lookUpContact(Integer id);

    List<AccountDTO> showAccount();

    AccountDTO lookUpAccount(Integer id);

    OpportunityDTO closeOpportunity(Integer id, String status);

    // Reports

    List<ReportDTO> reportLeadBySalesRep();

    List<ReportDTO> reportOpportunityBySalesRep();

    List<ReportDTO> reportOpportunityClosedWonBySalesRep();

    List<ReportDTO> reportOpportunityClosedLostBySalesRep();

    List<ReportDTO> reportOpportunityOpenBySalesRep();

    Integer reportMaxEmployeeCount();

    Integer reportMinEmployeeCount();

    double reportMeanEmployeeCount();

    double reportMedianEmployeeCount();

    Integer reportMaxOpportunitiesPerAccount();

    Integer reportMinOpportunitiesPerAccount();

    double reportMeanOpportunitiesPerAccount();

    double reportMedianOpportunitiesPerAccount();

    Integer reportMaxQuantity();

    Integer reportMinQuantity();

    double reportMeanQuantity();

    double reportMedianQuantity();


}
