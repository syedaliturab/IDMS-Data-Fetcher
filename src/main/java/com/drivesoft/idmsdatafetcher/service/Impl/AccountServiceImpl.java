package com.drivesoft.idmsdatafetcher.service.Impl;

import com.drivesoft.idmsdatafetcher.entity.Account;
import com.drivesoft.idmsdatafetcher.repository.AccountRepository;
import com.drivesoft.idmsdatafetcher.service.AccountService;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Override
    @Transactional
    public void addAccounts(List<Account> accountList) {
            accountRepository.insertOrUpdateAccount(accountList);
    }

    @Override
    public Page<Account> getAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }
}
