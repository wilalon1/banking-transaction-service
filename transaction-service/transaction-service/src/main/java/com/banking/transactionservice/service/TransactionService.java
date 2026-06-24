package com.banking.transactionservice.service;

import com.banking.transactionservice.model.Transaction;
import io.reactivex.rxjava3.core.*;

public interface TransactionService {

    Single<Transaction> create(Transaction tx);

    Observable<Transaction> findAll();

    Observable<Transaction> findByCustomerId(String customerId);

    Single<Transaction> update(String id, Transaction tx);

    Completable delete(String id);
}