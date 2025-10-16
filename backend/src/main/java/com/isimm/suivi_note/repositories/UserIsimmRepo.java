package com.isimm.suivi_note.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isimm.suivi_note.models.UserIsimm;


public interface UserIsimmRepo extends JpaRepository<UserIsimm,String> {
    Optional<UserIsimm> findByCin(String cin);

    boolean existsByCin(String cin);
}
