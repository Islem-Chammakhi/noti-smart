package com.isimm.suivi_note.brevo.service;

import com.isimm.suivi_note.brevo.contract.BrevoTemplate;
import com.isimm.suivi_note.services.EmailService;
import com.isimm.suivi_note.utils.email.EmailTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import sendinblue.ApiClient;
import sendinblue.ApiException;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailTo;

import java.util.List;

@Service
@ConditionalOnProperty(name = "email.service.provider", havingValue = "brevo")
public class BrevoService implements EmailService {
    private TransactionalEmailsApi apiInstance;


    public BrevoService(@Value("${email.key}") String apiKey) {

        ApiClient defaultClient = Configuration.getDefaultApiClient();
        var apiClient = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiClient.setApiKey(apiKey);

        System.out.println("Api Key ="+apiKey);
        apiInstance = new TransactionalEmailsApi();
    }

    public void sendEmail(String dstEmail, EmailTemplate brevoTemplate){
        SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
        sendSmtpEmail.templateId(brevoTemplate.template()); // Already comes with Sender's email and name
        sendSmtpEmail.setParams(brevoTemplate.params());
        SendSmtpEmailTo to = new SendSmtpEmailTo();
        to.email(dstEmail);
        sendSmtpEmail.to(List.of(to));

        try{
            apiInstance.sendTransacEmail(sendSmtpEmail);
        }
        catch (ApiException e){
            System.err.println("Exception when calling TransactionalEmailsApi#sendTransacEmail");
            System.err.println(e.getCode()+", "+ e.getMessage()+", "+e.getResponseBody()+", "+e.getResponseHeaders());
        }
    }

}
