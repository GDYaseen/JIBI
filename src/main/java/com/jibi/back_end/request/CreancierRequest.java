package com.jibi.back_end.request;

import lombok.Data;

@Data
public class CreancierRequest {
    private String creancierCode;
    private String creancierName;
    private String category;
    private CreanceRequest[] creances;
}
