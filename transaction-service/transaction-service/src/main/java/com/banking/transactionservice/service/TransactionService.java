package com.banking.transactionservice.service;

import com.banking.transactionservice.model.Transaction;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

import java.time.LocalDateTime;

public interface TransactionService {

    Single<Transaction> create(Transaction tx);

    Observable<Transaction> findAll();

    Observable<Transaction> findByCustomerId(String customerId);

    Single<Transaction> update(String id, Transaction tx);

    Completable delete(String id);

    Observable<Transaction> findByAccountAndDateRange(
            String accountId,
            LocalDateTime from,
            LocalDateTime to
    );

    Observable<Transaction> findLast10ByAccount(String accountId);
}