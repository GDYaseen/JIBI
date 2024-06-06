package com.jibi.back_end.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.jibi.back_end.models.User;
import com.jibi.back_end.repos.UserRepository;

import java.util.List;


@Transactional
@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;

    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    public User getUserById(Long id) {
        return this.userRepository.findById(id)
                .orElse(null);
    }


    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public User getUserByPhoneNumber(String phoneNumber) {
        return this.userRepository.findByPhoneNumber(phoneNumber)
                .orElse(null);
    }
}
