package com.jibi.back_end.request;

import lombok.Data;

@Data
public class PaymentBodyRequest {
    private Long impayeId;
    private Long accountId;
}
