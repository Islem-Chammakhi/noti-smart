package com.isimm.suivi_note.utils.email;

import java.util.Map;

public interface EmailTemplate {
    long template();
    Map<String, String> params();
}
