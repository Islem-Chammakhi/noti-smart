package com.isimm.suivi_note.exceptions;

import javax.naming.AuthenticationException;

public class InvalidOtpException extends RuntimeException {
    public InvalidOtpException(String message) {
        super(message);
    }
}
