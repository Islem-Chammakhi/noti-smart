package com.isimm.suivi_note.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isimm.suivi_note.models.User;


public interface UserRepo extends JpaRepository<User,String> {
    Optional<User> findByCin(String cin);

    boolean existsByCin(String cin);
}   
