package com.axelor.apps.event.service;

import java.math.BigDecimal;

import com.axelor.apps.event.db.EventRegistration;;

public interface EventRegistrationService {
	
	public BigDecimal calculateAmount(EventRegistration eventReg);

}
