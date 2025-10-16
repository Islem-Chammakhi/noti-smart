package com.isimm.suivi_note.services;

import org.springframework.stereotype.Service;

import com.isimm.suivi_note.dto.StudentDto;
import com.isimm.suivi_note.enums.Role;
import com.isimm.suivi_note.models.Student;
import com.isimm.suivi_note.repositories.AdminRepo;
import com.isimm.suivi_note.repositories.StudentRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StudentService {
    private final StudentRepo studentRepo;
    private final AdminRepo adminRepo;

    public Student addStudent(StudentDto studentDto){
        Student student = Student.builder()
                .cin(studentDto.cin())
                .firstName(studentDto.firstName())
                .lastName(studentDto.lastName())
                .email(studentDto.email())
                .password(studentDto.password())
                // .registrationNumber(studentDto.registrationNumber())
                .role(Role.STUDENT)
                .build();
        return studentRepo.save(student);

    }
}
