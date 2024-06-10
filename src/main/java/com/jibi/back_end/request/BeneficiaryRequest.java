package com.jibi.back_end.request;

import lombok.Data;

@Data
public class BeneficiaryRequest {

    private String phoneNumber;
    private String clientName;
    private Long userId;
}
