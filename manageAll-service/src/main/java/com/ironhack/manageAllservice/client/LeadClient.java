package com.ironhack.manageAllservice.client;

import com.ironhack.manageAllservice.controller.dtos.LeadDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("lead-service")
public interface LeadClient {

    @GetMapping("/lead")
    List<LeadDTO> getAll();

    @GetMapping("/lead/{id}")
    LeadDTO getById(@PathVariable Integer id);

    @PostMapping("/lead")
    LeadDTO add(@RequestBody LeadDTO leadDTO);
}
