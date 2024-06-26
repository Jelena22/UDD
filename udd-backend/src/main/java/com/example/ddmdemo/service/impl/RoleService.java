package com.example.ddmdemo.service.impl;

import com.example.ddmdemo.model.Authority;
import com.example.ddmdemo.respository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    public Authority findByName(String name) {
        Authority authority = roleRepository.findByName(name);
        return authority;
    }
}
