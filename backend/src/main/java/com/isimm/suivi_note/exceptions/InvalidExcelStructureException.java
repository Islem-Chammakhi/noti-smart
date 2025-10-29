package com.isimm.suivi_note.exceptions;

public class InvalidExcelStructureException extends RuntimeException {
    
    public InvalidExcelStructureException(String message){
        super(message);
    }
}
