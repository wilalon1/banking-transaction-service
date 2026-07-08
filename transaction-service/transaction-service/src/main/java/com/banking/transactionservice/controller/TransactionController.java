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

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public Single<Transaction> create(@RequestBody Transaction tx) {
        return service.create(tx);
    }

    @GetMapping
    public Observable<Transaction> findAll() {
        return service.findAll();
    }

    @GetMapping("/customer/{customerId}")
    public Observable<Transaction> findByCustomerId(@PathVariable String customerId) {
        return service.findByCustomerId(customerId);
    }

    @PutMapping("/{id}")
    public Single<Transaction> update(@PathVariable String id,
                                      @RequestBody Transaction tx) {
        return service.update(id, tx);
    }

    @DeleteMapping("/{id}")
    public Completable delete(@PathVariable String id) {
        return service.delete(id);
    }

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

    @GetMapping("/account/{accountId}/last10")
    public Observable<Transaction> getLast10(@PathVariable String accountId) {
        return service.findLast10ByAccount(accountId);
    }
}
