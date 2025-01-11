package com.drivesoft.idmsdatafetcher.repository;

import com.drivesoft.idmsdatafetcher.entity.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomAccountRepositoryImpl implements CustomAccountRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insertOrUpdateAccount(List<Account> accounts) {
        accounts.forEach(account -> entityManager.merge(account));
    }
}
