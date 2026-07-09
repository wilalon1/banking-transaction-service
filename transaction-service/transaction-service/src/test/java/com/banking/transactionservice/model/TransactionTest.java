package com.banking.transactionservice.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    void shouldCreateTransactionUsingNoArgsConstructorAndSetters() {

        LocalDateTime now = LocalDateTime.now();

        Transaction transaction = new Transaction();

        transaction.setId("1");
        transaction.setAccountId("ACC001");
        transaction.setCustomerId("CUS001");
        transaction.setType("CREDIT");
        transaction.setAmount(1500.0);
        transaction.setBalanceAfter(3500.0);
        transaction.setDate(now);

        assertEquals("1", transaction.getId());
        assertEquals("ACC001", transaction.getAccountId());
        assertEquals("CUS001", transaction.getCustomerId());
        assertEquals("CREDIT", transaction.getType());
        assertEquals(1500.0, transaction.getAmount());
        assertEquals(3500.0, transaction.getBalanceAfter());
        assertEquals(now, transaction.getDate());
    }

    @Test
    void shouldCreateTransactionUsingAllArgsConstructor() {

        LocalDateTime now = LocalDateTime.now();

        Transaction transaction = new Transaction(
                "1",
                "ACC001",
                "CUS001",
                "DEBIT",
                500.0,
                2500.0,
                now
        );

        assertEquals("1", transaction.getId());
        assertEquals("ACC001", transaction.getAccountId());
        assertEquals("CUS001", transaction.getCustomerId());
        assertEquals("DEBIT", transaction.getType());
        assertEquals(500.0, transaction.getAmount());
        assertEquals(2500.0, transaction.getBalanceAfter());
        assertEquals(now, transaction.getDate());
    }

    @Test
    void shouldCreateTransactionUsingBuilder() {

        LocalDateTime now = LocalDateTime.now();

        Transaction transaction = Transaction.builder()
                .id("1")
                .accountId("ACC001")
                .customerId("CUS001")
                .type("CREDIT")
                .amount(1000.0)
                .balanceAfter(4000.0)
                .date(now)
                .build();

        assertEquals("1", transaction.getId());
        assertEquals("ACC001", transaction.getAccountId());
        assertEquals("CUS001", transaction.getCustomerId());
        assertEquals("CREDIT", transaction.getType());
        assertEquals(1000.0, transaction.getAmount());
        assertEquals(4000.0, transaction.getBalanceAfter());
        assertEquals(now, transaction.getDate());
    }

    @Test
    void shouldVerifyEqualsHashCodeAndToString() {

        LocalDateTime now = LocalDateTime.now();

        Transaction transaction1 = Transaction.builder()
                .id("1")
                .accountId("ACC001")
                .customerId("CUS001")
                .type("CREDIT")
                .amount(1000.0)
                .balanceAfter(4000.0)
                .date(now)
                .build();

        Transaction transaction2 = Transaction.builder()
                .id("1")
                .accountId("ACC001")
                .customerId("CUS001")
                .type("CREDIT")
                .amount(1000.0)
                .balanceAfter(4000.0)
                .date(now)
                .build();

        assertEquals(transaction1, transaction2);
        assertEquals(transaction1.hashCode(), transaction2.hashCode());

        String toString = transaction1.toString();

        assertNotNull(toString);
        assertTrue(toString.contains("ACC001"));
        assertTrue(toString.contains("CUS001"));
        assertTrue(toString.contains("CREDIT"));
    }
}