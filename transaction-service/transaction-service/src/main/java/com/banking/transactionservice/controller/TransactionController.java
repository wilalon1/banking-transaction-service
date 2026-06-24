package com.banking.transactionservice.controller;

import com.banking.transactionservice.model.Transaction;
import com.banking.transactionservice.service.TransactionService;
import io.reactivex.rxjava3.core.*;
import org.springframework.web.bind.annotation.*;

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
}
