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

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElse(null);
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElse(null);
    }
}
