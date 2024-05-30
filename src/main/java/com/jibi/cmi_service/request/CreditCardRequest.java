package com.jibi.cmi_service.request;

import lombok.Data;

@Data
public class CreditCardRequest {
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;
    private String cvv;
    private String billingAddress;
}
