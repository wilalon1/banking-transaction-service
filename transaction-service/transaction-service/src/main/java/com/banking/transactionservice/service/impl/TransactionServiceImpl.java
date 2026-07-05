package com.banking.transactionservice.service.impl;

import com.banking.transactionservice.model.Transaction;
import com.banking.transactionservice.repository.TransactionRepository;
import com.banking.transactionservice.service.TransactionService;
import io.reactivex.rxjava3.core.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;
    private static final int MAX_FREE_TX = 10;
    private static final double COMMISSION = 2.0;

    public TransactionServiceImpl(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<Transaction> create(Transaction tx) {
        return Single.fromPublisher(repository.save(tx));
    }

    @Override
    public Observable<Transaction> findAll() {
        return Observable.fromPublisher(repository.findAll());
    }

    @Override
    public Observable<Transaction> findByCustomerId(String customerId) {
        return Observable.fromPublisher(repository.findByCustomerId(customerId));
    }

    @Override
    public Single<Transaction> update(String id, Transaction tx) {
        tx.setId(id);
        return Single.fromPublisher(repository.save(tx));
    }

    @Override
    public Completable delete(String id) {
        return Completable.fromPublisher(repository.deleteById(id));
    }

    @Override
    public Observable<Transaction> findByAccountAndDateRange(
            String accountId,
            LocalDateTime from,
            LocalDateTime to
    ) {

        return Observable.fromPublisher(
                repository.findByAccountIdAndDateBetween(accountId, from, to)
        );
    }

    @Override
    public Observable<Transaction> findLast10ByAccount(String accountId) {

        return Observable.fromPublisher(
                repository.findTop10ByAccountIdOrderByDateDesc(accountId)
        );
    }
}