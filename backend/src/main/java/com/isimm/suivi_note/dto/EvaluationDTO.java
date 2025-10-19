package com.isimm.suivi_note.dto;

import com.isimm.suivi_note.enums.EvaluationLabel;

public record EvaluationDTO(
    EvaluationLabel evaluationLabel,
    double coefficient
) {

}
