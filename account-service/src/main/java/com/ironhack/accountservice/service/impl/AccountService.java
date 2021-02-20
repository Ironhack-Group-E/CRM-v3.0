package com.ironhack.accountservice.service.impl;

import com.ironhack.accountservice.controller.dtos.AccountDTO;
import com.ironhack.accountservice.model.Account;
import com.ironhack.accountservice.repository.AccountRepository;
import com.ironhack.accountservice.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    public AccountDTO getAccount(Integer id) {

        if(accountRepository.existsById(id)) {

            Account account = accountRepository.findById(id).get();

            return new AccountDTO(account.getId(), account.getCompanyName(), account.getIndustry(),
                    account.getEmployeeCount(), account.getCity(), account.getCountry());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
    }

    public AccountDTO postAccount(AccountDTO accountDTO) {

        Account account = new Account(accountDTO.getCompanyName(), accountDTO.getIndustry(),
                accountDTO.getEmployeeCount(), accountDTO.getCity(), accountDTO.getCountry());

        account = accountRepository.save(account);

        accountDTO.setId(account.getId());
        return accountDTO;
    }

    public List<AccountDTO> getAllAccount() {

        List<Account> accountList = accountRepository.findAll();

        if (accountList.size() == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There are no accounts created");
        }

        List<AccountDTO> accountDTOList = new ArrayList<>();

        for(Account eachAccount: accountList){
            accountDTOList.add( new AccountDTO(eachAccount.getId(),
                    eachAccount.getCompanyName(),
                    eachAccount.getIndustry(),
                    eachAccount.getEmployeeCount(),
                    eachAccount.getCity(),
                    eachAccount.getCountry()));
        }
        return accountDTOList;
    }
}
