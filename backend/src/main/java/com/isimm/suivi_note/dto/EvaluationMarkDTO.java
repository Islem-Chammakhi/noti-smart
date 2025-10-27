package com.isimm.suivi_note.dto;

import com.isimm.suivi_note.enums.Eval;

import lombok.Builder;

@Builder
public record EvaluationMarkDTO(
    Eval label,
    double mark
) {

}
