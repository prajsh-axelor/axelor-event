package com.axelor.apps.event.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.axelor.apps.event.db.Discount;
import com.axelor.apps.event.db.Event;

public class DiscountServiceImpl implements DiscountService {

	@Override
	public boolean validateBeforeDays(Integer beforeDays, LocalDate regOpenDate, LocalDate regCloseDate) {
		// TODO Auto-generated method stub
		long noOfDay = ChronoUnit.DAYS.between(regOpenDate, regCloseDate);
		System.out.println("no of day : " + noOfDay);
		return beforeDays <= noOfDay ? true :false ;
	}

	@Override
	public BigDecimal calculateDiscountAmount(Discount discount, Event event) {
		return (discount.getDiscountPercent().multiply(event.getEventFee())).divide(BigDecimal.valueOf(100));
	}

}
