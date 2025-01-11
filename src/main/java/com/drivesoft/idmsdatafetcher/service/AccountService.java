package com.drivesoft.idmsdatafetcher.service;

import com.drivesoft.idmsdatafetcher.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService {
     void addAccounts(List<Account> accountList);
    Page<Account> getAccounts(Pageable pageable);

}
