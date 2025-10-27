package com.isimm.suivi_note.dto;

import com.isimm.suivi_note.enums.Eval;

public record EvaluationDTO(
    Eval evaluationLabel,
    double coefficient
) {

}
