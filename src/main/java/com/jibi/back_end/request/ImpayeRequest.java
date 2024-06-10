package com.jibi.back_end.request;

import lombok.Data;

@Data
public class ImpayeRequest {
    private Long creanceId;
    private String senderPhone;
    private String impayeType;
    private Double amount;
}
