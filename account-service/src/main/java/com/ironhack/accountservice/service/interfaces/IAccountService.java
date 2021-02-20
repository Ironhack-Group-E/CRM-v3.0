package com.ironhack.accountservice.service.interfaces;

import com.ironhack.accountservice.controller.dtos.AccountDTO;

import java.util.List;

public interface IAccountService {

    List<AccountDTO> getAllAccount();

    AccountDTO getAccount(Integer id);

    AccountDTO postAccount(AccountDTO accountDTO);
}
