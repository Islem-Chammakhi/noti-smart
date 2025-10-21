package com.isimm.suivi_note.dto;

import lombok.Builder;

@Builder
public record StudentMarksBySubjectDTO(
     String studentCin,
     String studentName,
     Double ds,
     Double exam,
     Double oralOrTp
) {


}
