package com.isimm.suivi_note.services;

import org.springframework.stereotype.Service;

import com.isimm.suivi_note.dto.StudentDto;
import com.isimm.suivi_note.models.Admin;
import com.isimm.suivi_note.models.Role;
import com.isimm.suivi_note.repositories.AdminRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepo adminRepo;

    public Admin addAdmin(StudentDto studentDto){
        Admin admin = Admin.builder()
                .cin(studentDto.cin())
                .firstName(studentDto.firstName())
                .lastName(studentDto.lastName())
                .email(studentDto.email())
                .password(studentDto.password())
                // .registrationNumber(studentDto.registrationNumber())
                .role(Role.ADMIN)
                .build();
        return adminRepo.save(admin);

    }
}
