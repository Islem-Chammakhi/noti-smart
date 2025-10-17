package com.isimm.suivi_note.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.isimm.suivi_note.enums.EvaluationLabel;
import com.isimm.suivi_note.models.SubjectEvalType;


public interface SubjectEvalTypeRepo extends JpaRepository<SubjectEvalType,Long> {

    @Query("SELECT s FROM SubjectEvalType s WHERE s.subject_id.id = :code AND s.eval_type.label = :label")
Optional<SubjectEvalType> findBySubjectCodeAndEvaluationTypeLabel(@Param("code") String code, @Param("label") EvaluationLabel label);

}
