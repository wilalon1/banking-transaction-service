package com.banking.transactionservice.controller;

import com.banking.transactionservice.model.Transaction;
import com.banking.transactionservice.service.TransactionService;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
/**
 * REST controller responsible for managing banking transactions.
 *
 * Provides operations to record, query, update,
 delete, and filter transactions by different criteria.
 */

@Tag(
        name = "Credits",
        description = "Credit-related transactions"
)
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService service;

    /**
     * Controller constructor.
     *
     * @param service service responsible for the business logic
     * of transactions.
     */
    public TransactionController(TransactionService service) {
        this.service = service;
    }

    /**
     * Registers a new transaction.
     *
     * @param tx transaction information.
     * @return registered transaction.
     */
    @PostMapping
    public Single<Transaction> create(@RequestBody Transaction tx) {
        return service.create(tx);
    }

    /**
     * Retrieves all logged transactions.
     *
     * @return reactive list of transactions.
     */
    @GetMapping
    public Observable<Transaction> findAll() {
        return service.findAll();
    }

    /**
     * Retrieves transactions made by a customer.
     *
     * @param customerId customer identifier.
     * @return transactions associated with the customer.
     */
    @GetMapping("/customer/{customerId}")
    public Observable<Transaction> findByCustomerId(@PathVariable String customerId) {
        return service.findByCustomerId(customerId);
    }
    /**
     * Updates transaction information.
     *
     * @param id Transaction identifier.
     * @param tx Updated data.
     * @return Updated transaction.
     */

    @PutMapping("/{id}")
    public Single<Transaction> update(@PathVariable String id,
                                      @RequestBody Transaction tx) {
        return service.update(id, tx);
    }

    /**
     * Deletes a transaction by its identifier.
     *
     * @param id Identifier of the transaction.
     * @return Reactive delete operation.
     */
    @DeleteMapping("/{id}")
    public Completable delete(@PathVariable String id) {
        return service.delete(id);
    }

    /**
     * Retrieves transactions from an account within a specified date range.
     * @param accountId account identifier.
     * @param from start date and time.
     * @param to end date and time.
     * @return transactions within the specified range.
     */
    @GetMapping("/account/{accountId}/range")
    public Observable<Transaction> getByDateRange(
            @PathVariable String accountId,
            @RequestParam String from,
            @RequestParam String to
    ) {

        return service.findByAccountAndDateRange(
                accountId,
                LocalDateTime.parse(from),
                LocalDateTime.parse(to)
        );
    }

    /**
     * Retrieves the last ten transactions for an account.
     *
     * @param accountId account identifier.
     * @return last ten recorded transactions.
     */
    @GetMapping("/account/{accountId}/last10")
    public Observable<Transaction> getLast10(@PathVariable String accountId) {
        return service.findLast10ByAccount(accountId);
    }
}
