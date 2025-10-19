package com.isimm.suivi_note.brevo.contract;

import com.isimm.suivi_note.utils.email.EmailTemplate;

public interface BrevoTemplate extends EmailTemplate {
    int OTP_TEMPLATE = 4;
    int PASSWORD_RESET_TEMPLATE=2;
}
