package com.ironhack.salesRepservice.controller.interfaces;

import com.ironhack.salesRepservice.controller.dtos.SalesRepDTO;

import java.util.List;

public interface ISalesRepController {

    SalesRepDTO getSalesRep(Integer id);

    List<SalesRepDTO> getAllSalesRep();

    SalesRepDTO postSalesRep(SalesRepDTO salesRepDTO);
}
