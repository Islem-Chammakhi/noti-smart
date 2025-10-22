package com.isimm.suivi_note.dto;

import lombok.Builder;

@Builder
public record SubjectGeneralAverageDTO(
         String subjectId,
     String subjectName,
     double average
) {

}
