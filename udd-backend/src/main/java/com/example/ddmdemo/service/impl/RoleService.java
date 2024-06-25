package com.example.ddmdemo.service.impl;

import com.example.ddmdemo.model.Role;
import com.example.ddmdemo.respository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    public Role findByName(String name) {
        Role authority = roleRepository.findByName(name);
        return authority;
    }
}
