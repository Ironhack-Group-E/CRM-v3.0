package com.ironhack.manageAllservice.service.interfaces;

import com.ironhack.manageAllservice.controller.dtos.*;
import com.ironhack.manageAllservice.controller.dtos.report.ReportDTO;

import java.util.List;

public interface IManageAllService {

    SalesRepDTO newSalesRep(SalesRepDTO salesRepDTO);

    List<SalesRepDTO> showSalesRep();

    SalesRepDTO getSalesRepById(Integer id);

    LeadDTO newLead(LeadDTO leadDTO);

    List<LeadDTO> getLeads();

    LeadDTO getLeadById(Integer id);

    OpportunityDTO convertLead(Integer id, Integer idAccount, PurchaseDTO purchaseDTO);

    OpportunityDTO convertLead(Integer id, PurchaseWithAccountDTO purchaseWithAccountDTO);

    List<OpportunityDTO> showOpportunity();

    OpportunityDTO lookUpOpportunity(Integer id);

    List<AccountDTO> showAccount();

    AccountDTO lookUpAccount(Integer id);

    List<ContactDTO> showContact();

    ContactDTO lookUpContact(Integer id);

    OpportunityDTO closeOpportunity(Integer id, String status);

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
