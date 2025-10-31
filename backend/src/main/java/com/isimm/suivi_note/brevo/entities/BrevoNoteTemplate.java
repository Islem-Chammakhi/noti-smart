package com.isimm.suivi_note.brevo.entities;

import com.isimm.suivi_note.brevo.contract.BrevoTemplate;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class BrevoNoteTemplate implements BrevoTemplate {
    double note;
    String eval;
    String matiere;

    @Override
    public long template() {
        return NOTE_TEMPLATE;
    }

    @Override
    public Map<String, String> params() {
        return Map.of("note", String.valueOf(note), "eval", eval, "matiere", matiere);
    }
}
