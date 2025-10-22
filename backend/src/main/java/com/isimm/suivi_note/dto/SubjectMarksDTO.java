package com.isimm.suivi_note.dto;

import lombok.Builder;

@Builder
public record SubjectMarksDTO(
     String subjectId,
     String subjectName,
     Double ds,
     Double exam,
     Double oralOrTp
) {

}
