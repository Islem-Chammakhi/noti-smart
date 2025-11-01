package com.isimm.suivi_note.fcm.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.isimm.suivi_note.dto.notification.NoteDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmService {

    private final FirebaseMessaging firebaseMessaging;

    public void sendMobileNote(NoteDTO dto){
        // This registration token comes from the client FCM SDKs.
        String registrationToken = "fuarIzpvS6663-4GO1k-8h:APA91bHlKMSq_JjS793zBqPDxf7csizLvv9QKTakNNvkC4bLX1Lf8UQB05lN0ara94CYBeUZSILoMLGbfa64A1vZsHp1viCuyEoyELRrCmrRlxS8L60nbhY";


        Message message = Message.builder()
                .putData("dateSaisie", dto.dateSaisie().toString())
                .putData("Matiere", dto.matiere())
                .putData("eval",dto.typeEval().name())
                .putData("note",String.valueOf(dto.value()))
                .setToken(registrationToken)
                .build();
        try{
            firebaseMessaging.send(message);
        }catch (FirebaseMessagingException e){
            log.error("Error sending FCM message: {}",e.getMessage(), e);
        }
    }
}
