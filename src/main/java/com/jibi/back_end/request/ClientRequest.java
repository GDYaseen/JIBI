package com.jibi.back_end.request;

import lombok.Data;

@Data
public class ClientRequest {
    private String phoneNumber;
    private String email;
    private String name;
    private PaymentAccountRequest paymentAccountRequest;
    private String password;
    private String carteRecto;
    private String carteVerso;

}
