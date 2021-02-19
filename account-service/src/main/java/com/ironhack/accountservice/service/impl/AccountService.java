package com.ironhack.accountservice.service.impl;

import com.ironhack.accountservice.controller.dtos.AccountDTO;
import com.ironhack.accountservice.model.Account;
import com.ironhack.accountservice.repository.AccountRepository;
import com.ironhack.accountservice.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    public AccountDTO getAccount(Integer id) {

        if(accountRepository.existsById(id)) {

            Account account = accountRepository.findById(id).get();

            return new AccountDTO(account.getId(), account.getCompanyName());
        }
        return null;
    }

    public AccountDTO postAccount(AccountDTO accountDTO) {
        return null;
    }
}
