package com.ironhack.salesRepservice.service.impl;

import com.ironhack.salesRepservice.controller.dtos.SalesRepDTO;
import com.ironhack.salesRepservice.model.SalesRep;
import com.ironhack.salesRepservice.repository.SalesRepRepository;
import com.ironhack.salesRepservice.service.exceptions.SalesRepNotFoundException;
import com.ironhack.salesRepservice.service.interfaces.ISalesRepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalesRepService implements ISalesRepService {

    @Autowired
    private SalesRepRepository salesRepRepository;

    public SalesRepDTO getSalesRep(Integer id) {

        if(salesRepRepository.existsById(id)) {

            SalesRep salesRep = salesRepRepository.findById(id).get();

            return new SalesRepDTO(salesRep.getId(), salesRep.getName());

        } else {
            throw new SalesRepNotFoundException("Sales rep not found");
        }
    }

    public List<SalesRepDTO> getAllSalesRep() {

        List<SalesRep> salesReps = salesRepRepository.findAll();

        List<SalesRepDTO> salesRepDTOS = new ArrayList<>();
        for (SalesRep salesRep : salesReps) {
            salesRepDTOS.add(new SalesRepDTO(salesRep.getId(), salesRep.getName()));
        }

        return salesRepDTOS;
    }

    public SalesRepDTO postSalesRep(SalesRepDTO salesRepDTO) {

        SalesRep salesRep = new SalesRep(salesRepDTO.getName());

        salesRep = salesRepRepository.save(salesRep);

        salesRepDTO.setId(salesRep.getId());

        return salesRepDTO;
    }
}
