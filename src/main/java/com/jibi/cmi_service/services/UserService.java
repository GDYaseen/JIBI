package com.jibi.cmi_service.services;

import com.jibi.cmi_service.models.User;
import com.jibi.cmi_service.repos.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
                .orElseThrow(()->new RuntimeException("User Not Found"));
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(()->new RuntimeException("User Not Found"));
    }
}
