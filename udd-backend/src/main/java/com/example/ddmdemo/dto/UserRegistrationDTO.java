package com.example.ddmdemo.dto;

import com.example.ddmdemo.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {
    private String email;
    private String password;
    private String name;
    private String surname;
    //private Role role;


}
