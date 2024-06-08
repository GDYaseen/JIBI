package com.jibi.back_end.request;


import java.util.List;

import lombok.Data;

@Data
public class CreancierRequest {
    private String creancierCode;
    private String creancierName;
    private String category;
    private Long[] creances;
}
