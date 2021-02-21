package com.ironhack.manageAllservice.controller.interfaces;

import com.ironhack.manageAllservice.controller.dtos.*;

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
}
