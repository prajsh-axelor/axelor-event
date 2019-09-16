package com.axelor.apps.event.web;

import com.axelor.apps.event.db.Discount;
import com.axelor.apps.event.db.Event;
import com.axelor.apps.event.db.EventRegistration;
import com.axelor.apps.event.service.DiscountService;
import com.axelor.apps.event.service.EventService;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;

public class EventController {

  @Inject EventService eventService;
  @Inject DiscountService discountService;

  public void countDetails(ActionRequest request, ActionResponse response) {
    Event event = request.getContext().asType(Event.class);

    response.setValues(eventService.countDetails(event));
    // change bigdecimal to int value.. : out of scope as event's setters take bigdecimal as
    // arguments

  }

  public void checkEventRegistrationListDate(ActionRequest request, ActionResponse response) {
    Event event = request.getContext().asType(Event.class);

    // check for
    if (event.getEventRegistrationList() != null) {
      for (EventRegistration eventReg : event.getEventRegistrationList()) {
        // if date outside of the registration dates.. show error
        if (event.getRegistrationOpenDate() != null
            && event.getRegistrationCloseDate() != null
            && eventReg.getRegistrationDateT() != null) {
          if (eventReg
                  .getRegistrationDateT()
                  .toLocalDate()
                  .isAfter(event.getRegistrationCloseDate())
              || eventReg
                  .getRegistrationDateT()
                  .toLocalDate()
                  .isBefore(event.getRegistrationOpenDate())) {
            response.setError("Registration date outside of range. Check again.");
            break;
          }
        }
      }
    }

    // check for valid before day
    if (event.getDiscountList() != null) {
      for (Discount discount : event.getDiscountList()) {
        if (discount.getBeforeDays() != null
            && event.getRegistrationOpenDate() != null
            && event.getRegistrationCloseDate() != null) {
          if (!discountService.validateBeforeDays(
              discount.getBeforeDays(),
              event.getRegistrationOpenDate(),
              event.getRegistrationCloseDate())) {
            // invalid before days
            response.setError("Before day of one of the discount is not valid. Check again");
            break;
          }
        }
      }
    }
  }
}
