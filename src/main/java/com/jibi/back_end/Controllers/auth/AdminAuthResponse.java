package com.jibi.back_end.Controllers.auth;

import com.jibi.back_end.models.Admin;

import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdminAuthResponse extends AuthenticationResponse{
    private Admin admin;
    private String token;
    private boolean isSuperAdmin;
}
