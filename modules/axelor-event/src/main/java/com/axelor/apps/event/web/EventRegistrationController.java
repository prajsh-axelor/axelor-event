package com.axelor.apps.event.web;

import com.axelor.apps.event.db.EventRegistration;
import com.axelor.apps.event.service.EventRegistrationService;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;

public class EventRegistrationController {
	@Inject 
	EventRegistrationService eventRegistrationService;
	
	public void calculateAmount(ActionRequest request, ActionResponse response) {
		 EventRegistration eventReg= request.getContext().asType(EventRegistration.class);
		 eventReg.setAmount(eventRegistrationService.calculateAmount(eventReg));
		 response.setValues(eventReg);
	}
	
}
