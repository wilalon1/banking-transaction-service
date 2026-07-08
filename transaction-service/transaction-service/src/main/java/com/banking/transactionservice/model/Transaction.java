package com.banking.transactionservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "transactions")
public class Transaction {

    @Id
    private String id;

    private String accountId;

    private String customerId;

    // CREDIT / DEBIT
    private String type;

    private Double amount;

    private Double balanceAfter;

    private LocalDateTime date;
}