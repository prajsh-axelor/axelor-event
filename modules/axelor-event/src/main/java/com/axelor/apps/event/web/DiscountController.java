package com.axelor.apps.event.web;

import java.math.BigDecimal;

import com.axelor.apps.event.db.Discount;
import com.axelor.apps.event.db.Event;
import com.axelor.apps.event.service.DiscountService;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;

public class DiscountController {
  @Inject DiscountService discountService;

  public void validateBeforeDays(ActionRequest request, ActionResponse response) {

    System.err.println(request.getContext().entrySet());
    Discount discount = request.getContext().asType(Discount.class);

    if (request.getContext().getParent() != null) {
      Event event = request.getContext().getParent().asType(Event.class);
      if (!discountService.validateBeforeDays(
          discount.getBeforeDays(),
          event.getRegistrationOpenDate(),
          event.getRegistrationCloseDate())) {
        // if before date not between regOpenDate and regCloseDate
        discount.setBeforeDays(0);
        // value not being reflected on the form
        //				 response.setValues(discount);
        response.setValue("beforeDays", null);
        response.setError("Invalid Before Day. Enter different value.");
        response.setReload(true);
      }
    }
  }

  public void calculateDiscountAmount(ActionRequest request, ActionResponse response) {
    Discount discount = request.getContext().asType(Discount.class);

    if (request.getContext().getParent().asType(Event.class) != null) {
      Event event = request.getContext().getParent().asType(Event.class);

      discount.setDiscountAmount(discountService.calculateDiscountAmount(discount, event));
      System.out.println("discount amount is : " + discount.getDiscountAmount());
      response.setValues(discount);
    }
  }
}
