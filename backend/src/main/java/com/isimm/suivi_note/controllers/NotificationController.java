package com.isimm.suivi_note.controllers;

import com.isimm.suivi_note.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/notify")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping(value = "/user/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connectUserToStream(@PathVariable String id){
        return notificationService.registerUser(id);
    }
}
