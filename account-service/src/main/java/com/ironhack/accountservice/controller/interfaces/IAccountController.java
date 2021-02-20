package com.ironhack.accountservice.controller.interfaces;

import com.ironhack.accountservice.controller.dtos.AccountDTO;

import java.util.List;

public interface IAccountController {

    List<AccountDTO> getAllAccount();

    AccountDTO getAccount(Integer id);

    AccountDTO postAccount(AccountDTO accountDTO);
}
