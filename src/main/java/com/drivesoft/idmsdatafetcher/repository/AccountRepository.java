package com.drivesoft.idmsdatafetcher.repository;
import com.drivesoft.idmsdatafetcher.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> ,CustomAccountRepository{
    Page<Account> findAll(Pageable pageable);
}
