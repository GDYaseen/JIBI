package com.jibi.back_end.Controllers;

import com.jibi.back_end.models.Beneficiaire;
import com.jibi.back_end.services.BeneficiaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/beneficiaire")
public class BeneficiaireController {

    @Autowired
    private BeneficiaireService beneficiaireService;

    @PostMapping("/create")
    public ResponseEntity<Beneficiaire> createBeneficiaire(@RequestParam Long accountNumber,@RequestParam String clientName,@RequestParam Long userId) {
        Beneficiaire beneficiaire = beneficiaireService.createBeneficiaire(accountNumber, clientName, userId);
        return ResponseEntity.ok(beneficiaire);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Beneficiaire>> getBenefitersForUser(@PathVariable Long userId) {
        List<Beneficiaire> benefitors = beneficiaireService.getBenefitersForUser(userId);
        return ResponseEntity.ok(benefitors);
    }
}
