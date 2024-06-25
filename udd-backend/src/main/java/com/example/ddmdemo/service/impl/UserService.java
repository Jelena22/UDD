package com.example.ddmdemo.service.impl;

import com.example.ddmdemo.dto.UserRegistrationDTO;
import com.example.ddmdemo.model.Role;
import com.example.ddmdemo.model.User;
import com.example.ddmdemo.respository.RoleRepository;
import com.example.ddmdemo.respository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] genSalt = new byte[16];
        random.nextBytes(genSalt);
        return genSalt;
    }

    private String generatePasswordWithSalt(String userPassword, String salt) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(userPassword);
        stringBuilder.append(salt);
        return stringBuilder.toString();
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public User registerUser(UserRegistrationDTO userRegistrationDTO) {
        User registeredUser = new User();
        registeredUser.setEmail(userRegistrationDTO.getEmail());
        byte[] salt = generateSalt();
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        //registeredUser.setSalt(encodedSalt);
        String passwordWithSalt = generatePasswordWithSalt(userRegistrationDTO.getPassword(), encodedSalt);
        String securePassword = hashPassword(passwordWithSalt);
        registeredUser.setPassword(securePassword);
        registeredUser.setName(userRegistrationDTO.getName());
        registeredUser.setSurname(userRegistrationDTO.getSurname());
        Role role = roleRepository.findByName("ROLE_REGULAR_USER");
        registeredUser.setRole(role);
        userRepository.save(registeredUser);
        //ConfirmationToken confirmationToken = confirmationTokenService.saveConfirmationToken(registeredUser);
        return registeredUser;

    }
}
