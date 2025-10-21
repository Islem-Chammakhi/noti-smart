package com.isimm.suivi_note.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.isimm.suivi_note.models.Mark;
import com.isimm.suivi_note.models.Student;
import com.isimm.suivi_note.models.Subject;

import java.util.List;


public interface MarkRepo extends JpaRepository<Mark,Long> {
    List<Mark> findByStudent(Student student_id);

    @Query("SELECT m FROM Mark m WHERE m.student = :student AND m.subjectEvalType.subject = :subject")
    List<Mark> findByStudentAndSubject(Student student,Subject subject);

    @Query("SELECT m FROM Mark m WHERE m.student.cin = :studentCin AND m.subjectEvalType.subject.id = :subjectId")
    List<Mark> findByStudentCinAndSubjectId(String studentCin,String subjectId );
}
