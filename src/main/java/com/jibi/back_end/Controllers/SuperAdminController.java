package com.jibi.back_end.Controllers;

import lombok.AllArgsConstructor;

import java.util.List;

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
    public ResponseEntity<?> createAdmin(@RequestBody AdminRequest superAdminRequest){
        SuperAdmin superAdmin = superAdminService.getSuperAdminByEmail(superAdminRequest.getEmail());
        if (superAdmin != null) {
            return new ResponseEntity<>("{\"message\":\"SuperAdmin Already exists\"}",HttpStatus.BAD_REQUEST);
        }

        SuperAdmin newSuperAdmin = SuperAdmin.builder()
                .email(superAdminRequest.getEmail())
                .name(superAdminRequest.getName())
                .password(passwordEncoder.encode(superAdminRequest.getPassword()))
                .build();

        SuperAdmin createdAdmin = superAdminService.saveSuperAdmin(newSuperAdmin);
        return new ResponseEntity<SuperAdmin>(createdAdmin, HttpStatus.OK);
    }

    @GetMapping("")
   public ResponseEntity<List<SuperAdmin>> getSuperAdmins(){
    List<SuperAdmin> list = superAdminService.getAllSuperAdmins();
    System.out.println("Admin list: "+list.size());
    return new ResponseEntity<>(list,HttpStatus.OK);
   }
   @PutMapping("/modify/{id}")
   public ResponseEntity<?> modifySuperAdmin(@RequestBody AdminRequest adminRequest,@PathVariable Long id){
    SuperAdmin admin = superAdminService.getSuperAdminById(id);
    if(admin==null){
        return new ResponseEntity<>("{\"message\":\"Super admin not found\"}",HttpStatus.NOT_FOUND);
    }
    SuperAdmin testAdmin = superAdminService.getSuperAdminByEmail(adminRequest.getEmail()) ;
    if (testAdmin!= null && testAdmin.getId()!=admin.getId()) {
        return new ResponseEntity<>("{\"message\":\"Email already exists\"}",HttpStatus.BAD_REQUEST);
    }

    SuperAdmin newAdmin = SuperAdmin.builder()
            .id(id)
            .email(adminRequest.getEmail())
            .name(adminRequest.getName())
            .password(passwordEncoder.encode(adminRequest.getPassword()))
            .build();

    SuperAdmin createdAdmin = superAdminService.saveSuperAdmin(newAdmin);
    return new ResponseEntity<SuperAdmin>(createdAdmin, HttpStatus.OK);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<?> deleteMapping(@PathVariable Long id){
        superAdminService.deleteSuperAdmin(id);
        return ResponseEntity.ok("{\"message\":\"Admin deleted\"}");
   }
}
