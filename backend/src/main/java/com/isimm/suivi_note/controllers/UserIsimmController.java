package com.isimm.suivi_note.controllers;


import org.springframework.web.bind.annotation.*;

import com.isimm.suivi_note.models.UserIsimm;
import com.isimm.suivi_note.repositories.UserIsimmRepo;

import lombok.RequiredArgsConstructor;

import java.util.List;


@RestController
@RequestMapping("/api/userisimm")
@RequiredArgsConstructor
public class UserIsimmController {

    private final UserIsimmRepo userIsimmRepo;

    @PostMapping()
    public UserIsimm postMethodName(@RequestBody UserIsimm userIsimm) {
        return userIsimmRepo.save(userIsimm);
    }

    @GetMapping()
    public List<UserIsimm> getMethodName() {
        return userIsimmRepo.findAll();
    }
    
}
