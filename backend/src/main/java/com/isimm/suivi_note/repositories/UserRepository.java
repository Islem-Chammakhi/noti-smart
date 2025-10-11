package com.isimm.suivi_note.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isimm.suivi_note.models.User;

public interface UserRepository extends JpaRepository<User,String> {

}
