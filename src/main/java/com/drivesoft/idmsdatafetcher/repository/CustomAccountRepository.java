package com.drivesoft.idmsdatafetcher.repository;

import com.drivesoft.idmsdatafetcher.entity.Account;

import java.util.List;

public interface CustomAccountRepository {

    public void insertOrUpdateAccount(List<Account> accounts);
}
