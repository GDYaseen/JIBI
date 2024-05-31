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

    public SuperAdmin addSuperAdmin(SuperAdmin superAdmin) {
        return superAdminRepository.save(superAdmin);
    }

    public void deleteSuperAdminById(Long id) {
        superAdminRepository.deleteById(id);
    }

    public SuperAdmin getSuperAdminById(Long id) {
        return superAdminRepository.findById(id)
                .orElse(null);
    }


    public List<SuperAdmin> getAllSuperAdmins() {
        return superAdminRepository.findAll();
    }
    public SuperAdmin getSuperAdminByEmail(String email) {
        return superAdminRepository.findByEmail(email)
                .orElse(null);
    }
    public SuperAdmin getSuperAdminByEmailAndPassword(String email,String password) {
        return superAdminRepository.findByEmailAndPassword(email,password)
                .orElse(null);
    }
}
