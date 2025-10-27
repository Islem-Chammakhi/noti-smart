package com.isimm.suivi_note.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isimm.suivi_note.models.TypeEvaluation;
import com.isimm.suivi_note.enums.Eval;


public interface TypeEvaluationRepo extends JpaRepository<TypeEvaluation,Long> {
    Optional<TypeEvaluation> findByLabelle(Eval label);


}
