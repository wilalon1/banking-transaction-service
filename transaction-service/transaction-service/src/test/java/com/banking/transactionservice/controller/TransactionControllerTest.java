package com.banking.transactionservice.controller;

import com.banking.transactionservice.config.SecurityTestConfig;
import com.banking.transactionservice.model.Transaction;
import com.banking.transactionservice.service.TransactionService;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebFluxTest(TransactionController.class)
@AutoConfigureWebTestClient
@Import(SecurityTestConfig.class)
class TransactionControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private TransactionService service;

    @Test
    void shouldCreateTransaction() {

        Transaction tx = new Transaction();

        when(service.create(any(Transaction.class)))
                .thenReturn(Single.just(tx));

        webTestClient.post()
                .uri("/api/transactions")
                .bodyValue(tx)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldFindAllTransactions() {

        when(service.findAll())
                .thenReturn(Observable.just(new Transaction()));

        webTestClient.get()
                .uri("/api/transactions")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldFindByCustomerId() {

        when(service.findByCustomerId(anyString()))
                .thenReturn(Observable.just(new Transaction()));

        webTestClient.get()
                .uri("/api/transactions/customer/123")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldUpdateTransaction() {

        Transaction tx = new Transaction();

        when(service.update(anyString(), any(Transaction.class)))
                .thenReturn(Single.just(tx));

        webTestClient.put()
                .uri("/api/transactions/1")
                .bodyValue(tx)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldDeleteTransaction() {

        when(service.delete(anyString()))
                .thenReturn(Completable.complete());

        webTestClient.delete()
                .uri("/api/transactions/1")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldGetByDateRange() {

        when(service.findByAccountAndDateRange(
                anyString(),
                any(LocalDateTime.class),
                any(LocalDateTime.class)))
                .thenReturn(Observable.just(new Transaction()));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/transactions/account/1/range")
                        .queryParam("from", "2024-01-01T00:00:00")
                        .queryParam("to", "2024-12-31T23:59:59")
                        .build())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldGetLast10() {

        when(service.findLast10ByAccount(anyString()))
                .thenReturn(Observable.just(new Transaction()));

        webTestClient.get()
                .uri("/api/transactions/account/1/last10")
                .exchange()
                .expectStatus().isOk();
    }
}