package com.isimm.suivi_note.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isimm.suivi_note.models.Etudiant;


public interface StudentRepo extends JpaRepository<Etudiant,String> {
    Optional<Etudiant>  findByCin (String cin);

    List<Etudiant> findByFiliereId(String filiereId);
}



