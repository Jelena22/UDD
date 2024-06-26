package com.example.ddmdemo.respository;

import com.example.ddmdemo.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Authority, Long> {
    Authority findByName(String name);
}
