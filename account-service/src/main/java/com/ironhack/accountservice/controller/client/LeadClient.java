package com.ironhack.accountservice.controller.client;


import com.ironhack.accountservice.controller.dtos.*;
import org.springframework.cloud.openfeign.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@FeignClient("lead-service")
public interface LeadClient {

    @GetMapping("/lead")
    @ResponseStatus(HttpStatus.OK)
    public List<LeadDTO> getAll();

    @GetMapping("/lead/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LeadDTO getById(@PathVariable Integer id);

    @PostMapping("/lead")
    @ResponseStatus(HttpStatus.CREATED)
    public LeadDTO add(@RequestBody LeadDTO leadDTO);
}
