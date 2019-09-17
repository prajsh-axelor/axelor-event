package com.axelor.apps.event.web;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import com.axelor.apps.event.db.Event;
import com.axelor.apps.event.db.EventRegistration;
import com.axelor.apps.message.db.EmailAddress;
import com.axelor.apps.message.db.Message;
import com.axelor.apps.message.db.repo.EmailAccountRepository;
import com.axelor.apps.message.service.MessageService;
import com.axelor.exception.AxelorException;
import com.axelor.inject.Beans;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;

public class EmailMessageController {

  @Inject private MessageService messageService;

  public void sendEmail(ActionRequest request, ActionResponse response) {

    Event event = request.getContext().asType(Event.class);

    // creating a set of address on which email is to be sent
    Set<EmailAddress> emailAddressSet = new HashSet<EmailAddress>();
    if (event != null) {
      // get the registration list
      List<EventRegistration> eventRegistrationsList = event.getEventRegistrationList();
      if (eventRegistrationsList != null) {
        // extract the email address to which email is to be send
        for (EventRegistration eventReg : eventRegistrationsList) {
          if (eventReg.getEmail() != null && !eventReg.getIsEmailSent()) {
            EmailAddress emailAdd = new EmailAddress();
            emailAdd.setAddress(eventReg.getEmail());
            emailAddressSet.add(emailAdd);
            eventReg.setIsEmailSent(true);
          }
        }
      }
      // if unsend address with unsend email is present send email
      if (!emailAddressSet.isEmpty()) {
        // create email message to send and call sendByEmail()
        Message message = new Message();
        // set mail account
        message.setMailAccount(Beans.get(EmailAccountRepository.class).all().fetchOne());
        message.setContent("thanks for registering in this event : " + event.getReference());
        message.setToEmailAddressSet(emailAddressSet);
        message.setSubject(event.getReference() + " information mail");
        try {
          messageService.sendByEmail(message);
        } catch (MessagingException | IOException | AxelorException e) {
          e.printStackTrace();
        }
        response.setFlash("Email sent successfully!!");
      } else {
        response.setFlash("No recieptant found");
      }
      response.setValue("eventRegistrationList", eventRegistrationsList);
    }
  }
}
