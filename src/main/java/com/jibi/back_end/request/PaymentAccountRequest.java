package com.jibi.back_end.request;

import lombok.Data;

@Data
public class PaymentAccountRequest {
    private CreditCardRequest creditCardRequest;
}
