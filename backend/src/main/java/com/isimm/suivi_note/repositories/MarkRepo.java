package com.isimm.suivi_note.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isimm.suivi_note.models.Mark;

public interface MarkRepo extends JpaRepository<Mark,Long> {

}
