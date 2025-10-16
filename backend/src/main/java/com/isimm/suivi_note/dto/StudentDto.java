package com.isimm.suivi_note.dto;


public record StudentDto(
    String cin,
    String firstName,
    String lastName,
    String email,
    String password
) {

}
