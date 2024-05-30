package com.jibi.cmi_service.request;

import lombok.Data;

@Data
public class PaymentBodyRequest {
    private Long impayeId;
    private Long accountId;
}
