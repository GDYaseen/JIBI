    package com.jibi.back_end.Controllers;
    
    import com.jibi.back_end.Controllers.auth.AdminAuthResponse;
import com.jibi.back_end.Controllers.auth.AuthenticationRequest;
    import com.jibi.back_end.Controllers.auth.AuthenticationResponse;
    import com.jibi.back_end.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
    import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

    @RestController
    @CrossOrigin
    @RequiredArgsConstructor
    @RequestMapping("/api/auth")
    public class AuthController {

        private final AuthenticationService service;

        // @PostMapping("/register")
        // public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        //     System.out.println("*********\t\tAccessing /api/auth/register");
        //     return ResponseEntity.ok(service.register(request));
        // }
        @PostMapping("/client/login")
        public ResponseEntity<?> loginClient(@RequestBody AuthenticationRequest request){
            try{
            System.out.println("*********\t\tAccessing /api/auth/client/login");
            return new ResponseEntity<AuthenticationResponse>(service.login(request,"Client"),HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        }
        @PostMapping("/agent/login")
        public ResponseEntity<?> loginAgent(@RequestBody AuthenticationRequest request){
            try{
                System.out.println("*********\t\tAccessing /api/auth/agent/login");
            return new ResponseEntity<AuthenticationResponse>(service.login(request,"Agent"),HttpStatus.OK);
        }
            catch(Exception e){
                return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
            }
        }
        @PostMapping("/admin/login")
        public ResponseEntity<AdminAuthResponse> loginAdmin(@RequestBody AuthenticationRequest request){
            System.out.println("*********\t\tAccessing /api/auth/admin/login");
            return ResponseEntity.ok(service.loginAdmin(request));
        }
        @PostMapping("/clientprof/login")
        public ResponseEntity<AuthenticationResponse> loginClientProf(@RequestBody AuthenticationRequest request){
            System.out.println("*********\t\tAccessing /api/auth/clientprof/login");
            return ResponseEntity.ok(service.login(request,"ClientProf"));
        }
        @PostMapping("/superadmin/login")
        public ResponseEntity<AuthenticationResponse> loginSuperAdmin(@RequestBody AuthenticationRequest request){
            System.out.println("*********\t\tAccessing /api/auth/superadmin/login");
            return ResponseEntity.ok(service.login(request,"SuperAdmin"));
        }
    }


