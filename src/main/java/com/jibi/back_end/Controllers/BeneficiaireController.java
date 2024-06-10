package com.jibi.back_end.Controllers;

import com.jibi.back_end.models.Beneficiaire;
import com.jibi.back_end.request.BeneficiaryRequest;
import com.jibi.back_end.services.BeneficiaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/beneficiaire")
public class BeneficiaireController {

    @Autowired
    private BeneficiaireService beneficiaireService;

    @PostMapping("/create")
    public ResponseEntity<Beneficiaire> createBeneficiaire(@RequestBody BeneficiaryRequest request) {
        Beneficiaire beneficiaire = beneficiaireService.createBeneficiaire(request.getPhoneNumber(), request.getClientName(), request.getUserId());
        return ResponseEntity.ok(beneficiaire);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Beneficiaire>> getBenefitersForUser(@PathVariable Long userId) {
        List<Beneficiaire> benefitors = beneficiaireService.getBenefitersForUser(userId);
        return ResponseEntity.ok(benefitors);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        try{
            this.beneficiaireService.delete(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "success");
            return ResponseEntity.ok(response);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
