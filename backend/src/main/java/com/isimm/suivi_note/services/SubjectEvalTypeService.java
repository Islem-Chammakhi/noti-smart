package com.isimm.suivi_note.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.isimm.suivi_note.enums.EvaluationLabel;
import com.isimm.suivi_note.models.SubjectEvalType;
import com.isimm.suivi_note.repositories.SubjectEvalTypeRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubjectEvalTypeService {
    private final SubjectEvalTypeRepo subjectEvalTypeRepo;

    public SubjectEvalType findBySubjectAndEvalType(String code,String label){
            SubjectEvalType result = subjectEvalTypeRepo.findBySubjectCodeAndEvaluationTypeLabel(code, EvaluationLabel.valueOf(label))
                    .orElseThrow(() -> new RuntimeException("❌ "+code+" as subject id and with label "+label+" not found !"));
            return result;
    }
}
