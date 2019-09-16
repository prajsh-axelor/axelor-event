package com.axelor.apps.event.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.axelor.apps.event.db.Discount;
import com.axelor.apps.event.db.Event;

public interface DiscountService {
  public boolean validateBeforeDays(
      Integer beforeDays, LocalDate regOpenDate, LocalDate regCloseDate);

  public BigDecimal calculateDiscountAmount(Discount discount, Event event);
}
