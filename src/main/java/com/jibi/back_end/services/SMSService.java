package com.jibi.back_end.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jibi.back_end.config.VonageConfig;
import com.jibi.back_end.config.VonageInitializer;

import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;


@Service
public class SMSService {

    private final VonageConfig vonageConfig;

    @Autowired
    public SMSService(VonageConfig vonageConfig) {
        this.vonageConfig = vonageConfig;
    }
    public void sendSMS(String text, String target) {
        // Message message = Message.creator(
        //         new PhoneNumber(target),
        //         new PhoneNumber(twilioConfig.getNumber()),
        //         text)
            // .create();
TextMessage message = new TextMessage(
                vonageConfig.getAccountSid(), // Sender ID or phone number
                target, // Destination phone number
                text
        );
        SmsSubmissionResponse response = VonageInitializer.vonageClient.getSmsClient().submitMessage(message);

if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
    System.out.println("Message sent successfully.");
} else {
    System.out.println("Message failed with error: " + response.getMessages().get(0).getErrorText());
}}
}
