package com.ironhack.manageAllservice.client;

import com.ironhack.manageAllservice.controller.dtos.SalesRepDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("salesRep-service")
public interface SalesRepClient {

    @GetMapping("/sales-rep")
    List<SalesRepDTO> getAllSalesRep();

    @GetMapping("/sales-rep/{id}")
    SalesRepDTO getSalesRep(@PathVariable Integer id);

    @PostMapping("/sales-rep")
    SalesRepDTO postSalesRep(@RequestBody SalesRepDTO salesRepDTO);
}
