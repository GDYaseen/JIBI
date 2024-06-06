package com.jibi.back_end.Controllers;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.jibi.back_end.models.Admin;
import com.jibi.back_end.request.AdminRequest;
import com.jibi.back_end.services.AdminService;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class AdminController {

    private AdminService adminService;
    private final PasswordEncoder passwordEncoder;
    
    @PostMapping("/create")
    public ResponseEntity<Admin> createAdmin(@RequestBody AdminRequest adminRequest){
        Admin admin = adminService.getAdminByEmail(adminRequest.getEmail());
        if (admin != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Admin newAdmin = Admin.builder()
                .email(adminRequest.getEmail())
                .name(adminRequest.getName())
                .password(passwordEncoder.encode(adminRequest.getPassword()))
                .build();

        Admin createdAdmin = adminService.saveAdmin(newAdmin);
        return new ResponseEntity<>(createdAdmin, HttpStatus.OK);
    }

    @GetMapping("")
   public ResponseEntity<List<Admin>> getAdmins(){
    List<Admin> list = adminService.getAllAdmins();
    System.out.println("Admin list: "+list.size());
    return new ResponseEntity<>(list,HttpStatus.OK);
   }
}
