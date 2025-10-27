package com.isimm.suivi_note.services;

import com.isimm.suivi_note.dto.notification.NoteDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class NotificationService {

    public final Map<String, SseEmitter> userEmitters = new ConcurrentHashMap<>();

    public SseEmitter registerUser(String id) {
        var emitter = new SseEmitter((long)Short.MAX_VALUE);

        emitter.onTimeout(()->{
            log.warn("onTimeout on emtter of id={}",id);
            userEmitters.remove(id);
        });
        emitter.onError((e)->{
            log.error("onError on emitter Of id={}: {}", id, e.getMessage(), e);
            userEmitters.remove(id);
        });

        emitter.onCompletion(()->{
            log.info("Completed emitter of id= {}",id);
        });

        userEmitters.put(id, emitter);
        log.info("Connected user id={}",id);
        return emitter;
    }

    public void sendNote(NoteDTO noteDTO, String cin){
        var emitter = userEmitters.get(cin);
        if(emitter!=null){
            try{
                emitter.send(SseEmitter.event()
                        .name("Notify note")
                        .data(noteDTO)
                );
            }catch (IOException e){
                log.error("Error sending to user id {}. message: {}", cin,e.getMessage(), e);
            }
        }
        //log.warn("There's no connected user of cin ={}", cin);

    }

}
