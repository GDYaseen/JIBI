package com.jibi.back_end.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.jibi.back_end.models.Admin;
import com.jibi.back_end.repos.AdminRepository;

import java.util.List;


@Transactional
@AllArgsConstructor
@Service
public class AdminService {

    private AdminRepository adminRepository;

    public Admin addAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public void deleteAdminById(Long id) {
        adminRepository.deleteById(id);
    }

    public Admin getAdminById(Long id) {
        return adminRepository.findById(id)
                .orElse(null);
    }


    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }
    public Admin getAdminByEmail(String email) {
        return adminRepository.findByEmail(email)
                .orElse(null);
    }
    public Admin getAdminByEmailAndPassword(String email,String password) {
        return adminRepository.findByEmailAndPassword(email,password)
                .orElse(null);
    }
}
