package com.jibi.back_end.request;

import lombok.Data;

@Data
public class UserRequest {
    private String phoneNumber;
    private String email;
    private String name;
    private PaymentAccountRequest paymentAccountRequest;
}
