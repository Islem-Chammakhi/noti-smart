package com.isimm.suivi_note.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isimm.suivi_note.models.AverageSubject;
import com.isimm.suivi_note.models.Student;

import java.util.List;
import java.util.Optional;
import com.isimm.suivi_note.models.Subject;



public interface AverageSubjectRepo extends JpaRepository<AverageSubject,Long> {
    Optional<List<AverageSubject>> findByStudent(Student student);

    Optional<AverageSubject>  findByStudentAndSubject(Student student, Subject subject);


}
