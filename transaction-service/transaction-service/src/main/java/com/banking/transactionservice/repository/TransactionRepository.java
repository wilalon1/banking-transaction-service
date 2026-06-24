package com.banking.transactionservice.repository;

import com.banking.transactionservice.model.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {

    Flux<Transaction> findByCustomerId(String customerId);
    Flux<Transaction> findByAccountId(String accountId);

    Mono<Long> countByAccountId(String accountId);
}