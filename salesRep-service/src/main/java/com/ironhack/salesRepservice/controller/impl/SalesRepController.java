package com.ironhack.salesRepservice.controller.impl;

import com.ironhack.salesRepservice.controller.dtos.SalesRepDTO;
import com.ironhack.salesRepservice.controller.interfaces.ISalesRepController;
import com.ironhack.salesRepservice.service.interfaces.ISalesRepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SalesRepController implements ISalesRepController {

    @Autowired
    private ISalesRepService salesRepService;

    @GetMapping("/sales-rep/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SalesRepDTO getSalesRep(@PathVariable Integer id) {
        return salesRepService.getSalesRep(id);
    }

    @GetMapping("/sales-rep")
    @ResponseStatus(HttpStatus.OK)
    public List<SalesRepDTO> getAllSalesRep() {
        return salesRepService.getAllSalesRep();
    }

    @PostMapping("/sales-rep")
    @ResponseStatus(HttpStatus.CREATED)
    public SalesRepDTO postSalesRep() {
        return salesRepService.postSalesRep();
    }
}
