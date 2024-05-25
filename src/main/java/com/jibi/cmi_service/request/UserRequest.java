package com.jibi.cmi_service.request;

import lombok.Data;

@Data
public class UserRequest {
    private String phoneNumber;
    private String email;
    private String name;
    private PaymentAccountRequest paymentAccountRequest;
}
