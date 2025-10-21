package com.isimm.suivi_note.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OtpDTO(@JsonProperty("otp") String value) {
}
