package com.axelor.apps.event.service;

import java.math.BigDecimal;
import java.util.List;

import com.axelor.apps.event.db.Event;
import com.axelor.apps.event.db.EventRegistration;

public class EventServiceImpl implements EventService{

	@Override
	public Event countDetails(Event event) {
		
		List<EventRegistration> eventRegistrationList = event.getEventRegistrationList();
		if(eventRegistrationList != null) {
			BigDecimal amountCollected = BigDecimal.ZERO;
			for(EventRegistration eventReg : eventRegistrationList) {
				amountCollected = amountCollected.add(eventReg.getAmount());
			}
			event.setAmountCollected(amountCollected);
			event.setTotalEntry(eventRegistrationList.size());
			event.setTotalDiscount((event.getEventFee().multiply(BigDecimal.valueOf(eventRegistrationList.size()))).subtract(amountCollected));
		}
		
		return event;
	}

}
