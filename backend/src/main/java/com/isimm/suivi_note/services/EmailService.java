package com.isimm.suivi_note.services;

import com.isimm.suivi_note.utils.email.EmailTemplate;

public interface EmailService {
    void sendEmail(String dst, EmailTemplate template);
}
