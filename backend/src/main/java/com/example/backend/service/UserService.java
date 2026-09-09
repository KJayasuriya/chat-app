package com.example.backend.service;

import com.example.backend.model.UserModel;
import com.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserModel addUser(UserModel user) {
        user.setCreated_at(
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return userRepository.save(user);
    }

    public UserModel login(UserModel user) {
        UserModel existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser == null) {
            return null;
        }
        if (!existingUser.getPassword().equals(user.getPassword())) {
            return null;
        }
        return existingUser;
    }
}
