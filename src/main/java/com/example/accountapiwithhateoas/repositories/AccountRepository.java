package com.example.accountapiwithhateoas.repositories;

import com.example.accountapiwithhateoas.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findAccountByAccountNumber(String account);

    @Query("UPDATE Account a " + "SET a.balance = ?2, a.modifiedAt = ?3 WHERE a.accountNumber = ?1")
    @Modifying
    @Transactional
    void updatedBalance(String accountNumber, Double balance, Date date);

    @Query("UPDATE Account a SET a.balance = a.balance + ?1 WHERE a.accountNumber = ?2")
    @Modifying
    @Transactional
    void deposit(Double amount, String accountNumber);

    @Query("UPDATE Account a SET a.balance = a.balance - ?1 WHERE a.accountNumber = ?2")
    @Modifying
    @Transactional
    void withdraw(Double amount, String accountNumber);

}
