package com.isimm.suivi_note.exceptions;

public class NoteExistException extends RuntimeException {
    public NoteExistException(String message) {
        super(message);
    }
}
