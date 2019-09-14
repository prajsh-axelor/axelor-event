package com.axelor.apps.event.web;


import com.axelor.apps.event.db.Event;
import com.axelor.apps.event.service.EventService;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;

public class EventController {
	
	@Inject 
	EventService eventService;
	
	 public void countDetails(ActionRequest request, ActionResponse response) {
		 Event event = request.getContext().asType(Event.class);
		 
		 response.setValues(eventService.countDetails(event));
		 
	 }
}
