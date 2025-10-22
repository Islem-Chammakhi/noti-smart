package com.isimm.suivi_note.dto;

import lombok.Builder;

@Builder

public record SubjectAverageStatsDTO(
     String subjectId,
     String subjectName,
     Long  passed,
     Long  unpassed
) {

}
