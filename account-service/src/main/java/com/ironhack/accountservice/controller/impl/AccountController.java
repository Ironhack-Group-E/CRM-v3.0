package com.ironhack.accountservice.controller.impl;

import com.ironhack.accountservice.controller.dtos.AccountDTO;
import com.ironhack.accountservice.controller.interfaces.IAccountController;
import com.ironhack.accountservice.service.impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AccountController implements IAccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/account")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountDTO> getAllAccount() {
        return accountService.getAllAccount();
    }

    @GetMapping("/account/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDTO getAccount(@PathVariable Integer id) {
        return accountService.getAccount(id);
    }

    @PostMapping("/account")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDTO postAccount(@RequestBody @Valid AccountDTO accountDTO) {
        return accountService.postAccount(accountDTO);
    }

    @GetMapping("/account/max-employee-count")
    @ResponseStatus(HttpStatus.OK)
    public Integer getMaxEmployeeCount() {
        return accountService.getMaxEmployeeCount();
    }

    @GetMapping("/account/min-employee-count")
    @ResponseStatus(HttpStatus.OK)
    public Integer getMinEmployeeCount() {
        return accountService.getMinEmployeeCount();
    }

    @GetMapping("/account/mean-employee-count")
    @ResponseStatus(HttpStatus.OK)
    public double getMeanEmployeeCount() {
        return accountService.getAverageEmployeeCount();
    }

    @GetMapping("/account/median-employee-count")
    @ResponseStatus(HttpStatus.OK)
    public double getMedianEmployeeCount() {
        return accountService.getMedianEmployeeCount();
    }

    @GetMapping("/account/max-oppos")
    @ResponseStatus(HttpStatus.OK)
    public Integer getMaxOpportunitiesPerAccount() {
        return accountService.getMaxOpportunitiesPerAccount();
    }

    @GetMapping("/account/min-oppos")
    @ResponseStatus(HttpStatus.OK)
    public Integer getMinOpportunitiesPerAccount() {
        return accountService.getMinOpportunitiesPerAccount();
    }

    @GetMapping("/account/mean-oppos")
    @ResponseStatus(HttpStatus.OK)
    public double getMeanOpportunitiesPerAccount() {
        return accountService.getAvgOpportunitiesPerAccount();
    }

    @GetMapping("/account/median-oppos")
    @ResponseStatus(HttpStatus.OK)
    public double getMedianOpportunitiesPerAccount() {
        return accountService.getMedianOpportunitiesPerAccount();
    }
}
