package com.isimm.suivi_note.brevo.entities;

import com.isimm.suivi_note.brevo.contract.BrevoTemplate;

import java.util.Map;

public class BrevoOTPTemplate implements BrevoTemplate {
    String otp;

    public BrevoOTPTemplate(String otp){
        this.otp = otp;
    }
    @Override
    public long template() {
        return OTP_TEMPLATE;
    }

    @Override
    public Map<String, String> params() {
        return Map.of("otp", otp);
    }
}
