package com.jibi.back_end.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MonthlyTransactionSummary {
    private String month;
    private BigDecimal earned;
    private BigDecimal spent;

    public MonthlyTransactionSummary(String month, BigDecimal earned, BigDecimal spent) {
        this.month = month;
        this.earned = earned;
        this.spent = spent;
    }
}
