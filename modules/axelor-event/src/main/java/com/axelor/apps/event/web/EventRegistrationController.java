package com.axelor.apps.event.web;

import com.axelor.apps.event.db.Event;
import com.axelor.apps.event.db.EventRegistration;
import com.axelor.apps.event.service.EventRegistrationService;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;

public class EventRegistrationController {
  @Inject EventRegistrationService eventRegistrationService;

  public void calculateAmount(ActionRequest request, ActionResponse response) {

    EventRegistration eventReg = request.getContext().asType(EventRegistration.class);

    if (eventReg.getEvent() != null) {
      eventReg.setAmount(eventRegistrationService.calculateAmount(eventReg));
      response.setValues(eventReg);
    }
  }

  public void setParentEvent(ActionRequest request, ActionResponse response) {

    EventRegistration eventReg = request.getContext().asType(EventRegistration.class);

    if (eventReg.getEvent() == null) {
      Event event = request.getContext().getParent().asType(Event.class);
      eventReg.setEvent(event);
      System.out.println("Event = " + eventReg.getEvent());
      response.setValues(eventReg);
    }
  }
}
