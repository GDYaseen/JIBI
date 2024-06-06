package com.jibi.back_end.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.jibi.back_end.models.SuperAdmin;
import com.jibi.back_end.repos.SuperAdminRepository;

import java.util.List;


@Transactional
@AllArgsConstructor
@Service
public class SuperAdminService {

    private SuperAdminRepository superAdminRepository;

    public SuperAdmin saveSuperAdmin(SuperAdmin superAdmin) {
        return this.superAdminRepository.save(superAdmin);
    }

    public void deleteSuperAdmin(Long id) {
        this.superAdminRepository.deleteById(id);
    }

    public SuperAdmin getSuperAdminById(Long id) {
        return this.superAdminRepository.findById(id)
                .orElse(null);
    }


    public List<SuperAdmin> getAllSuperAdmins() {
        return this.superAdminRepository.findAll();
    }
    public SuperAdmin getSuperAdminByEmail(String email) {
        return this.superAdminRepository.findByEmail(email)
                .orElse(null);
    }
    public SuperAdmin getSuperAdminByEmailAndPassword(String email,String password) {
        return this.superAdminRepository.findByEmailAndPassword(email,password)
                .orElse(null);
    }
}
