package com.jibi.back_end.Controllers;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.jibi.back_end.models.SuperAdmin;
import com.jibi.back_end.request.AdminRequest;
import com.jibi.back_end.services.SuperAdminService;

@RestController
@RequestMapping("/api/v1/superadmin")
@AllArgsConstructor
public class SuperAdminController {

    private SuperAdminService superAdminService;
    private final PasswordEncoder passwordEncoder;
    @PostMapping("/create")
    public ResponseEntity<SuperAdmin> createAdmin(@RequestBody AdminRequest superAdminRequest){
        SuperAdmin superAdmin = superAdminService.getSuperAdminByEmail(superAdminRequest.getEmail());
        if (superAdmin != null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        SuperAdmin newSuperAdmin = SuperAdmin.builder()
                .email(superAdminRequest.getEmail())
                .name(superAdminRequest.getName())
                .password(passwordEncoder.encode(superAdminRequest.getPassword()))
                .build();

        SuperAdmin createdAdmin = superAdminService.saveSuperAdmin(newSuperAdmin);
        return new ResponseEntity<>(createdAdmin, HttpStatus.OK);
    }
}
