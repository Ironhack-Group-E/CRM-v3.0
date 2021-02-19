package com.ironhack.accountservice.controller.interfaces;

import com.ironhack.accountservice.controller.dtos.AccountDTO;

public interface IAccountController {

    AccountDTO getAccount(Integer id);

    AccountDTO postAccount(AccountDTO accountDTO);
}
