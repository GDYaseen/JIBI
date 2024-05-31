package com.jibi.back_end.request;

import lombok.Data;

@Data
public class AdminRequest {
    private String email;
    private String name;
    private String password;
}
