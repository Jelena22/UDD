package com.example.ddmdemo.respository;


import com.example.ddmdemo.model.Law;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LawRepository extends JpaRepository<Law, Integer> {
}
