package com.isimm.suivi_note.models;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@Embeddable
@Data
public class UniteEnsPK {
    private String ueId;
    private String filiereId;
}
