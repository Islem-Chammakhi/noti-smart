package com.isimm.suivi_note.exceptions;

public class DataAlreadyExistsException extends RuntimeException {

    public DataAlreadyExistsException(String message){
        super(message);
    }
}
