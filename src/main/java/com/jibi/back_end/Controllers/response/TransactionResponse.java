package com.jibi.back_end.Controllers.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.jibi.back_end.Enum.TransactionStatus;
import com.jibi.back_end.dto.UserDto;
import com.jibi.back_end.models.Impaye;
import com.jibi.back_end.models.Transaction;

import lombok.Data;

@Data
public class TransactionResponse {
    private Long id;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private TransactionStatus status;
    private Impaye impaye;
    private UserDto sender;
    private UserDto receiver;

    public TransactionResponse(Transaction transaction,UserDto sender,UserDto receiver) {
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.impaye = transaction.getImpaye();
        this.transactionDate = transaction.getTransactionDate();
        this.status = transaction.getStatus();
        this.sender = sender;
        this.receiver = receiver;
    }

    // getters and setters
}
