package com.isimm.suivi_note.dto;

import com.isimm.suivi_note.enums.EvaluationLabel;

import lombok.Builder;

@Builder
public record EvaluationMarkDTO(
    EvaluationLabel label,
    double mark
) {

}
