package com.isimm.suivi_note.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isimm.suivi_note.models.EvaluationType;
import com.isimm.suivi_note.models.Subject;
import com.isimm.suivi_note.models.SubjectEvalType;


public interface SubjectEvalTypeRepo extends JpaRepository<SubjectEvalType,Long> {
    
    Optional<SubjectEvalType>  findBySubject_idAndEval_type(Subject subject_id,EvaluationType eval_type);

    Optional<SubjectEvalType> findBySubject_id_CodeAndEval_type_Label(String code, String label);
}
