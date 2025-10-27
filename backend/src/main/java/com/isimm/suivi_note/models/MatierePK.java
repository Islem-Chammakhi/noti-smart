package com.isimm.suivi_note.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Embeddable
public class MatierePK {
    private String matiereId;

    @Embedded
    private UniteEnsPK ueId;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MatierePK matierePK)) return false;
        return Objects.equals(matiereId, matierePK.matiereId) && Objects.equals(ueId, matierePK.ueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matiereId, ueId);
    }
}
