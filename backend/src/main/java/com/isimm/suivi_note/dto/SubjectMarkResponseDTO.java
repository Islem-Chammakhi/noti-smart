package com.isimm.suivi_note.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record SubjectMarkResponseDTO(
    String subjectName,
    List<EvaluationMarkDTO> evaluations

) {

}
