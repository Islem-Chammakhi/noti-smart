package com.isimm.suivi_note.utils;

import java.security.SecureRandom;

public class OtpGenerator {
    public static String generateOtp(){
        return String.valueOf(100000 + new SecureRandom().nextInt(900000));
    }

}
