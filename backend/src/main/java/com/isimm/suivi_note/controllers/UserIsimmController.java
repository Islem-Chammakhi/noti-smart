package com.isimm.suivi_note.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isimm.suivi_note.models.UserIsimm;
import com.isimm.suivi_note.repositories.UserIsimmRepo;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/userisimm")
@RequiredArgsConstructor
public class UserIsimmController {

    private final UserIsimmRepo userIsimmRepo;

    @PostMapping()
    public UserIsimm postMethodName(@RequestBody UserIsimm userIsimm) {
        return userIsimmRepo.save(userIsimm);
    }

    @GetMapping()
    public List<UserIsimm> getMethodName(@RequestParam String param) {
        return userIsimmRepo.findAll();
    }
    
    
}
