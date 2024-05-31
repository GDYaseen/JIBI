package com.jibi.back_end.Controllers;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jibi.back_end.models.Admin;
import com.jibi.back_end.request.AdminRequest;
import com.jibi.back_end.services.AdminService;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class AdminController {

    private AdminService adminService;

    @PostMapping("/create")
    public ResponseEntity<Admin> createAdmin(@RequestBody AdminRequest adminRequest){
        Admin admin = adminService.getAdminByEmail(adminRequest.getEmail());
        if (admin != null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Admin newAdmin = Admin.builder()
                .email(adminRequest.getEmail())
                .name(adminRequest.getName())
                .password("12345")
                .build();

        Admin createdAdmin = adminService.addAdmin(newAdmin);
        return new ResponseEntity<>(createdAdmin, HttpStatus.OK);
    }

   
}
