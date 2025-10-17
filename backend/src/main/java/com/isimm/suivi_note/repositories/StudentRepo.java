package com.isimm.suivi_note.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isimm.suivi_note.models.Student;


public interface StudentRepo extends JpaRepository<Student,String> {
    Optional<Student>  findByCin (String cin);
}



