package com.isimm.suivi_note.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.isimm.suivi_note.models.SubjectEvalType;
import com.isimm.suivi_note.repositories.SubjectEvalTypeRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubjectEcalTypeService {
    private final SubjectEvalTypeRepo subjectEvalTypeRepo;

    public Optional<SubjectEvalType> findBySubjectAndEvalType(String code,String label){
            return subjectEvalTypeRepo.findBySubject_id_CodeAndEval_type_Label(code,label);
    }
}
