package com.ironhack.salesRepservice.service.interfaces;

import com.ironhack.salesRepservice.controller.dtos.SalesRepDTO;

import java.util.List;

public interface ISalesRepService {
    SalesRepDTO getSalesRep(Integer id);

    List<SalesRepDTO> getAllSalesRep();

    SalesRepDTO postSalesRep();
}
