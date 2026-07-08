package com.banking.transactionservice.service.impl;

import com.banking.transactionservice.model.Transaction;
import com.banking.transactionservice.repository.TransactionRepository;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private TransactionServiceImpl service;

    private Transaction transaction;

    @BeforeEach
    void setUp() {
        transaction = new Transaction();
        transaction.setId("1");
        transaction.setCustomerId("customer1");
        transaction.setAccountId("account1");
        transaction.setDate(LocalDateTime.now());
    }

    @Test
    void shouldCreateTransaction() {

        when(repository.save(any(Transaction.class)))
                .thenReturn(Mono.just(transaction));

        Transaction result = service.create(transaction).blockingGet();

        assertNotNull(result);
        assertEquals("1", result.getId());

        verify(repository).save(transaction);
    }

    @Test
    void shouldFindAllTransactions() {

        when(repository.findAll())
                .thenReturn(Flux.just(transaction));

        var list = service.findAll().toList().blockingGet();

        assertEquals(1, list.size());

        verify(repository).findAll();
    }

    @Test
    void shouldFindByCustomerId() {

        when(repository.findByCustomerId("customer1"))
                .thenReturn(Flux.just(transaction));

        var list = service.findByCustomerId("customer1")
                .toList()
                .blockingGet();

        assertEquals(1, list.size());
        assertEquals("customer1", list.get(0).getCustomerId());

        verify(repository).findByCustomerId("customer1");
    }

    @Test
    void shouldUpdateTransaction() {

        when(repository.save(any(Transaction.class)))
                .thenReturn(Mono.just(transaction));

        Transaction result = service.update("1", transaction).blockingGet();

        assertNotNull(result);
        assertEquals("1", result.getId());

        verify(repository).save(any(Transaction.class));
    }

    @Test
    void shouldDeleteTransaction() {

        when(repository.deleteById("1"))
                .thenReturn(Mono.empty());

        Completable result = service.delete("1");

        result.blockingAwait();

        verify(repository).deleteById("1");
    }

    @Test
    void shouldFindByAccountAndDateRange() {

        LocalDateTime from = LocalDateTime.now().minusDays(10);
        LocalDateTime to = LocalDateTime.now();

        when(repository.findByAccountIdAndDateBetween(anyString(), any(), any()))
                .thenReturn(Flux.just(transaction));

        var list = service.findByAccountAndDateRange("account1", from, to)
                .toList()
                .blockingGet();

        assertEquals(1, list.size());

        verify(repository)
                .findByAccountIdAndDateBetween("account1", from, to);
    }

    @Test
    void shouldFindLast10ByAccount() {

        when(repository.findTop10ByAccountIdOrderByDateDesc("account1"))
                .thenReturn(Flux.just(transaction));

        var list = service.findLast10ByAccount("account1")
                .toList()
                .blockingGet();

        assertEquals(1, list.size());

        verify(repository)
                .findTop10ByAccountIdOrderByDateDesc("account1");
    }

    @Test
    void shouldReturnEmptyFallbackFindByCustomerId() {

        var result = service
                .fallbackFindByCustomerId("customer1", new RuntimeException())
                .toList()
                .blockingGet();

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnEmptyFallbackFindByAccountAndDateRange() {

        var result = service
                .fallbackFindByAccountAndDateRange(
                        "account1",
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        new RuntimeException())
                .toList()
                .blockingGet();

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnEmptyFallbackFindLast10ByAccount() {

        var result = service
                .fallbackFindLast10ByAccount("account1", new RuntimeException())
                .toList()
                .blockingGet();

        assertTrue(result.isEmpty());
    }
}