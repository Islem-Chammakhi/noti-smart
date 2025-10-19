package com.isimm.suivi_note.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record SubjectResponseDTO(
    String subjectName,
    String subjectId,
    double coefficient,
    List<EvaluationDTO> evaluations
) {

}
