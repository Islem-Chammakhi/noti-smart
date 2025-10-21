package com.isimm.suivi_note.dto;

import lombok.Builder;

@Builder
public record UserDTO(
        String cin,
        String nom,
        String role
) {
}
