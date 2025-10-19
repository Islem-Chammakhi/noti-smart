package com.isimm.suivi_note.dto;

public record AuthOtpLoginReq(
        String cin,
        String password,
        String otp
) {
}
