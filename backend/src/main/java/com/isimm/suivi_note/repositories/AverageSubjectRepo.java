package com.isimm.suivi_note.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.isimm.suivi_note.dto.SubjectAverageStatsDTO;
import com.isimm.suivi_note.dto.SubjectGeneralAverageDTO;
import com.isimm.suivi_note.models.AverageSubject;
import com.isimm.suivi_note.models.Student;

import java.util.List;
import java.util.Optional;
import com.isimm.suivi_note.models.Subject;



public interface AverageSubjectRepo extends JpaRepository<AverageSubject,Long> {
    Optional<List<AverageSubject>> findByStudent(Student student);

    Optional<AverageSubject>  findByStudentAndSubject(Student student, Subject subject);

        @Query("""
        SELECT new com.isimm.suivi_note.dto.SubjectGeneralAverageDTO(
            s.id, s.nom, AVG(a.average)
        )
        FROM AverageSubject a
        JOIN a.subject s
        JOIN a.student st
        WHERE st.filiere.id = :filiereId
        GROUP BY s.id, s.nom
        ORDER BY s.nom ASC
    """)
    List<SubjectGeneralAverageDTO> findGeneralAveragePerSubjectByFiliere(String filiereId);

        @Query("""
        SELECT new com.isimm.suivi_note.dto.SubjectAverageStatsDTO(
            s.id,
            s.nom,
            SUM(CASE WHEN a.average >= 10 THEN 1 ELSE 0 END),
            SUM(CASE WHEN a.average < 10 THEN 1 ELSE 0 END)
        )
        FROM AverageSubject a
        JOIN a.subject s
        JOIN a.student st
        WHERE st.filiere.id = :filiereId
        GROUP BY s.id, s.nom
        ORDER BY s.nom ASC
    """)
    List<SubjectAverageStatsDTO> findAverageStatsPerSubjectByFiliere(String filiereId);

}
