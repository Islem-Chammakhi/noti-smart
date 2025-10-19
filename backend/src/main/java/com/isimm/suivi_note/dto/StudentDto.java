package com.isimm.suivi_note.dto;


public record StudentDTO(
    String cin,
    String firstName,
    String lastName,
    String email,
    String password
) {

}
