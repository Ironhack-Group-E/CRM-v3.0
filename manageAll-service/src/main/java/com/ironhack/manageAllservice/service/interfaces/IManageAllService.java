package com.ironhack.manageAllservice.service.interfaces;

import com.ironhack.manageAllservice.controller.dtos.*;

import java.util.List;

public interface IManageAllService {

    SalesRepDTO newSalesRep(SalesRepDTO salesRepDTO);

    List<SalesRepDTO> showSalesRep();

    SalesRepDTO getSalesRepById(Integer id);

    LeadDTO newLead(LeadDTO leadDTO);

    List<LeadDTO> showLeads();

    LeadDTO getLeadById(Integer id);

    List<OpportunityDTO> showOpportunity();

    OpportunityDTO lookUpOpportunity(Integer id);

    List<AccountDTO> showAccount();

    AccountDTO lookUpAccount(Integer id);

    List<ContactDTO> showContact();

    ContactDTO lookUpContact(Integer id);

    OpportunityDTO closeOpportunity(Integer id, String status);
}