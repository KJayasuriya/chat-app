package com.example.backend.service;

import com.example.backend.model.UserModel;
import com.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserModel> getUsers() {
        return userRepository.findAll();
    }

    public static UserModel addUser(UserModel userModel) {
        return UserRepository.save(userModel);
    }
}
