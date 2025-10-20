package com.isimm.suivi_note.dto;


public record AdminDTO(
    String cin,
    String firstName,
    String lastName,
    String email,
    String password
) {}