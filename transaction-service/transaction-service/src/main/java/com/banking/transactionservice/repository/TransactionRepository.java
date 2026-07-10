package com.banking.transactionservice.repository;

import com.banking.transactionservice.model.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;


/**
 * Reactive repository for transaction management in MongoDB.
 * Provides CRUD operations and custom queries
 * related to banking transactions.
 */
public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
    /**
     * Retrieves all transactions for a customer.
     *
     * @param customerId Customer identifier.
     * @return Customer transaction flow.
     */
    Flux<Transaction> findByCustomerId(String customerId);
    /**
     * Retrieves all transactions for an account.
     *
     * @param accountId account identifier.
     * @return account transaction flow.
     */
    Flux<Transaction> findByAccountId(String accountId);
    /**
     * Counts the number of transactions recorded for an account.
     *
     * @param accountId account identifier.
     * @return number of transactions.
     */
    Mono<Long> countByAccountId(String accountId);
    /**
     * Retrieves transactions from an account within a date range.
     * @param accountId account identifier.
     * @param from start date and time.
     * @param to end date and time.
     * @return transaction flow found.
     */
    Flux<Transaction> findByAccountIdAndDateBetween(
            String accountId,
            LocalDateTime from,
            LocalDateTime to
    );
    /**
     * Retrieves the last ten transactions for an account,
     * sorted from newest to oldest.
     * @param accountId account identifier.
     * @return stream with the last ten transactions.
     */
    Flux<Transaction> findTop10ByAccountIdOrderByDateDesc(String accountId);
}