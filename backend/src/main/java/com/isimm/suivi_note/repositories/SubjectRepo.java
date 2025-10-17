package com.isimm.suivi_note.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isimm.suivi_note.models.Subject;
import java.util.Optional;


public interface SubjectRepo  extends JpaRepository<Subject,String>{
    Optional<Subject> findById(String id);
}
