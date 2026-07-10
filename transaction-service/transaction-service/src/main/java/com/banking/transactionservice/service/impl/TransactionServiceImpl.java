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

/**
 * Service implementation responsible for managing banking transactions.
 *
 * Provides business logic for creating, retrieving, updating and deleting
 * transactions using reactive programming with RxJava.
 */
@Service
public class TransactionServiceImpl implements TransactionService {
    /**
     * Repository used to access transaction data.
     */
    private final TransactionRepository repository;
    /**
     * Maximum number of free transactions allowed.
     */
    private static final int MAX_FREE_TX = 10;
    /**
     * Commission applied after exceeding the free transaction limit.
     */
    private static final double COMMISSION = 2.0;
    /**
     * Creates a new service instance.
     *
     * @param repository transaction repository.
     */

    /**
     * Creates a new transaction.
     *
     * @param tx transaction information.
     * @return the created transaction.
     */
    public TransactionServiceImpl(TransactionRepository repository) {
        this.repository = repository;
    }
    /**
     * Creates a new transaction.
     *
     * @param tx transaction information.
     * @return the created transaction.
     */


    @Override
    public Single<Transaction> create(Transaction tx) {
        return Single.fromPublisher(repository.save(tx));
    }
    /**
     * Retrieves all registered transactions.
     *
     * @return stream of transactions.
     */
    @Override
    public Observable<Transaction> findAll() {
        return Observable.fromPublisher(repository.findAll());
    }
    /**
     * Retrieves all transactions associated with a customer.
     *
     * Uses a Circuit Breaker to improve fault tolerance.
     *
     * @param customerId customer identifier.
     * @return stream of customer transactions.
     */
    @Override
    @CircuitBreaker(name = "transactionService", fallbackMethod = "fallbackFindByCustomerId")
    public Observable<Transaction> findByCustomerId(String customerId) {
        return Observable.fromPublisher(
                repository.findByCustomerId(customerId)
                        .timeout(Duration.ofSeconds(2))
        );
    }
    /**
     * Fallback method executed when retrieving customer transactions fails.
     *
     * @param customerId customer identifier.
     * @param ex exception that caused the failure.
     * @return an empty stream.
     */
    public Observable<Transaction> fallbackFindByCustomerId(String customerId, Throwable ex) {
        System.out.println("Transaction service fallback: " + ex.getMessage());
        return Observable.empty();
    }
    /**
     * Updates an existing transaction.
     *
     * @param id transaction identifier.
     * @param tx updated transaction information.
     * @return updated transaction.
     */
    @Override
    public Single<Transaction> update(String id, Transaction tx) {
        tx.setId(id);
        return Single.fromPublisher(repository.save(tx));
    }
    /**
     * Deletes a transaction.
     *
     * @param id transaction identifier.
     * @return completion signal.
     */
    @Override
    public Completable delete(String id) {
        return Completable.fromPublisher(repository.deleteById(id));
    }
    /**
     * Retrieves transactions for an account within a date range.
     *
     * @param accountId account identifier.
     * @param from start date.
     * @param to end date.
     * @return matching transactions.
     */
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
    /**
     * Fallback method for account transaction search by date range.
     *
     * @param accountId account identifier.
     * @param from start date.
     * @param to end date.
     * @param ex exception that caused the failure.
     * @return an empty stream.
     */
    public Observable<Transaction> fallbackFindByAccountAndDateRange(String accountId, LocalDateTime from, LocalDateTime to, Throwable ex) {
        System.out.println("Transaction service fallback: " + ex.getMessage());
        return Observable.empty();
    }
    /**
     * Retrieves the last ten transactions for an account.
     *
     * @param accountId account identifier.
     * @return stream containing the latest ten transactions.
     */
    @Override
    @CircuitBreaker(name = "transactionService", fallbackMethod = "fallbackFindLast10ByAccount")
    public Observable<Transaction> findLast10ByAccount(String accountId) {

        return Observable.fromPublisher(
                repository.findTop10ByAccountIdOrderByDateDesc(accountId)
                        .timeout(Duration.ofSeconds(2))
        );
    }
    /**
     * Fallback method executed when retrieving the latest transactions fails.
     *
     * @param accountId account identifier.
     * @param ex exception that caused the failure.
     * @return an empty stream.
     */
    public Observable<Transaction> fallbackFindLast10ByAccount(String accountId, Throwable ex) {
        System.out.println("Transaction service fallback: " + ex.getMessage());
        return Observable.empty();
    }
}