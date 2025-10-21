package com.isimm.suivi_note.dto.notification;

import java.time.LocalDateTime;

public record NoteDTO(
        double value,
        LocalDateTime dateSaisie,
        TypeEval typeEval,
        String matiere
) {
}
/* type Note={
    id:number,
    value:number,
    dateSaisi: Date,
    type: TypeEval,
    matiere: Matiere

enum Evaluation{
    DS,EXAM,ORALE,TP
}

type TypeEval={
    id: number,
    libelle: Evaluation,
    coef: number
}

type Matiere={
    id: number,
    nom: string,
    coef: number,
    Notes: Note[] | null
}

}*/