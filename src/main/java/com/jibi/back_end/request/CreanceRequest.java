package com.jibi.back_end.request;


import lombok.Data;

@Data
public class CreanceRequest {
    private String creanceCode;
    private String creanceName;
    private String formData;
}
