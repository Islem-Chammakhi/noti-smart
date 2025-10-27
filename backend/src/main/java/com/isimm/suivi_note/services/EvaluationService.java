package com.isimm.suivi_note.services;

import com.isimm.suivi_note.dto.EvaluationDTO;

import com.isimm.suivi_note.models.TypeEvaluation;
import com.isimm.suivi_note.repositories.TypeEvaluationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationService {
    private final TypeEvaluationRepo typeEvalRepo;

    public List<EvaluationDTO> toProjection(Collection<TypeEvaluation> typesList){
        return typesList.stream()
                .map(typeEvaluation ->
                        new EvaluationDTO(typeEvaluation.getLabelle(),typeEvaluation.getCoefficient())
                ).toList();
    }
}
