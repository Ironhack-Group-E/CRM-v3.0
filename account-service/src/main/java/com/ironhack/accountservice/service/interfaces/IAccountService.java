package com.ironhack.accountservice.service.interfaces;

import com.ironhack.accountservice.controller.dtos.AccountDTO;

public interface IAccountService {
    AccountDTO getAccount(Integer id);

    AccountDTO postAccount(AccountDTO accountDTO);
}
