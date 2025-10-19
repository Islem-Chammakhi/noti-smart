package com.isimm.suivi_note;

import com.isimm.suivi_note.brevo.entities.BrevoOTPTemplate;
import com.isimm.suivi_note.brevo.service.BrevoService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SuiviNoteApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SuiviNoteApplication.class, args);
		/* Test Email service
		
		BrevoService service = context.getBean(BrevoService.class);

		System.out.println("Sending email...");
		service.sendEmail("med.yassine.kharrat@gmail.com", new BrevoOTPTemplate("12345"));*/

	}

}
