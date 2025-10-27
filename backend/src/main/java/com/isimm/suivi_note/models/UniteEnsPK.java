package com.isimm.suivi_note.models;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;


@NoArgsConstructor
@Embeddable
@Data
public class UniteEnsPK {
    private String ueId;
    private String filiereId;
}
