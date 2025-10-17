package com.isimm.suivi_note.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isimm.suivi_note.models.EvaluationType;
import com.isimm.suivi_note.enums.EvaluationLabel;


public interface EvaluationTypeRepo extends JpaRepository<EvaluationType,Long> {
    Optional<EvaluationType> findByLabel(EvaluationLabel label);
}
