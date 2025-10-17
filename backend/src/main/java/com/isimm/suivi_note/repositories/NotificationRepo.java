package com.isimm.suivi_note.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isimm.suivi_note.models.Notification;

public interface NotificationRepo  extends JpaRepository<Notification,Long>{

}
