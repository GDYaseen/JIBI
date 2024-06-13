package com.jibi.back_end.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class MappedTransaction {

    private String sender;
    private String receiver;

    private LocalDateTime transactionDate;

    private String status;
}
