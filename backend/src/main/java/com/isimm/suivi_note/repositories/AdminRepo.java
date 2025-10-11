package com.isimm.suivi_note.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isimm.suivi_note.models.Admin;

public interface AdminRepo extends JpaRepository <Admin,String> {

}
