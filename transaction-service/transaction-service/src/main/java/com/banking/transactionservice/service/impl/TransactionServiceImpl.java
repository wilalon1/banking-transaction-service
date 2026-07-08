package com.banking.transactionservice.service.impl;

import com.banking.transactionservice.model.Transaction;
import com.banking.transactionservice.repository.TransactionRepository;
import com.banking.transactionservice.service.TransactionService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import org.springframework.stereotype.Service;

import java.time.Duration;
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
    @CircuitBreaker(name = "transactionService", fallbackMethod = "fallbackFindByCustomerId")
    public Observable<Transaction> findByCustomerId(String customerId) {
        return Observable.fromPublisher(
                repository.findByCustomerId(customerId)
                        .timeout(Duration.ofSeconds(2))
        );
    }

    public Observable<Transaction> fallbackFindByCustomerId(String customerId, Throwable ex) {
        System.out.println("Transaction service fallback: " + ex.getMessage());
        return Observable.empty();
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
    @CircuitBreaker(name = "transactionService", fallbackMethod = "fallbackFindByAccountAndDateRange")
    public Observable<Transaction> findByAccountAndDateRange(
            String accountId,
            LocalDateTime from,
            LocalDateTime to
    ) {

        return Observable.fromPublisher(
                repository.findByAccountIdAndDateBetween(accountId, from, to)
                        .timeout(Duration.ofSeconds(2))
        );
    }

    public Observable<Transaction> fallbackFindByAccountAndDateRange(String accountId, LocalDateTime from, LocalDateTime to, Throwable ex) {
        System.out.println("Transaction service fallback: " + ex.getMessage());
        return Observable.empty();
    }

    @Override
    @CircuitBreaker(name = "transactionService", fallbackMethod = "fallbackFindLast10ByAccount")
    public Observable<Transaction> findLast10ByAccount(String accountId) {

        return Observable.fromPublisher(
                repository.findTop10ByAccountIdOrderByDateDesc(accountId)
                        .timeout(Duration.ofSeconds(2))
        );
    }

    public Observable<Transaction> fallbackFindLast10ByAccount(String accountId, Throwable ex) {
        System.out.println("Transaction service fallback: " + ex.getMessage());
        return Observable.empty();
    }
}