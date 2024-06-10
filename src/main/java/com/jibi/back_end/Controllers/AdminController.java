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
    public ResponseEntity<?> createAdmin(@RequestBody AdminRequest adminRequest){
        if(adminRequest.getEmail()==null){
            return new ResponseEntity<>("{\"message\":\"No Email provided\"}",HttpStatus.BAD_REQUEST);
        }
        Admin admin = adminService.getAdminByEmail(adminRequest.getEmail());
        if (admin != null) {
            return new ResponseEntity<>("{\"message\":\"Admin not found.\"}",HttpStatus.NOT_FOUND);
        }

        Admin newAdmin = Admin.builder()
                .email(adminRequest.getEmail())
                .name(adminRequest.getName())
                .password(passwordEncoder.encode(adminRequest.getPassword()))
                .build();

        Admin createdAdmin = adminService.saveAdmin(newAdmin);
        return new ResponseEntity<Admin>(createdAdmin, HttpStatus.OK);
    }

    @GetMapping("")
   public ResponseEntity<List<Admin>> getAdmins(){
    List<Admin> list = adminService.getAllAdmins();
    return new ResponseEntity<>(list,HttpStatus.OK);
   }
   @PutMapping("/modify/{id}")
   public ResponseEntity<?> modifyAdmin(@RequestBody AdminRequest adminRequest,@PathVariable Long id){
    Admin admin = adminService.getAdminById(id);
    if(admin==null){
        return new ResponseEntity<>("{\"message\":\"Admin with that is is not found.\"}",HttpStatus.NOT_FOUND);
    }
    Admin testAdmin = adminService.getAdminByEmail(adminRequest.getEmail()) ;
    if (testAdmin!= null && testAdmin.getId()!=admin.getId()) {
        return new ResponseEntity<>("{\"message\":\"Email already exists\"}",HttpStatus.BAD_REQUEST);
    }

    Admin newAdmin = Admin.builder()
            .id(id)
            .email(adminRequest.getEmail())
            .name(adminRequest.getName())
            .password(passwordEncoder.encode(adminRequest.getPassword()))
            .build();

    Admin createdAdmin = adminService.saveAdmin(newAdmin);
    return new ResponseEntity<Admin>(createdAdmin, HttpStatus.OK);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<?> deleteMapping(@PathVariable Long id){
        adminService.deleteAdmin(id);
        return ResponseEntity.ok("{\"message\":\"Admin Deleted\"}");
   }
}
