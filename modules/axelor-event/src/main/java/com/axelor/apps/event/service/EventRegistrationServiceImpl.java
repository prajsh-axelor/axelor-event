package com.axelor.apps.event.service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.axelor.apps.event.db.Discount;
import com.axelor.apps.event.db.Event;
import com.axelor.apps.event.db.EventRegistration;

public class EventRegistrationServiceImpl implements EventRegistrationService{

	@Override
	public BigDecimal calculateAmount(EventRegistration eventReg) {
		Event event = eventReg.getEvent();
		BigDecimal discountAmount = BigDecimal.ZERO;
		if(event != null) {
			List<Discount> discountList = event.getDiscountList();
			
			long daysLeft = ChronoUnit.DAYS.between(eventReg.getRegistrationDateT().toLocalDate(),event.getRegistrationCloseDate());
			
			for(Discount discount : discountList) {
				if(discount.getBeforeDays() <= daysLeft && discount.getDiscountAmount().compareTo(discountAmount) == 1 	) {
					discountAmount = discount.getDiscountAmount();
				}
			}
		}
		
		return event.getEventFee().subtract(discountAmount);
	}

}
