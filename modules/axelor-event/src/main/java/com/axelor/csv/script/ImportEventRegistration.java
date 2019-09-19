package com.axelor.csv.script;

import java.util.Map;

import com.axelor.apps.event.db.Event;
import com.axelor.apps.event.db.EventRegistration;

public class ImportEventRegistration {

  public Object checkCapacity(Object bean, Map<String, Object> values) {

	  if (bean instanceof EventRegistration) {
      EventRegistration eventReg = (EventRegistration) bean;
      Event event = eventReg.getEvent();
      if (event.getCapacity() <= event.getTotalEntry()) {
    	  return null;
//        try {
//          throw new AxelorException(
//              TraceBackRepository.CATEGORY_CONFIGURATION_ERROR,
//              I18n.get(IExceptionEvent.EVENT_CAPACITY_FULL));
//        } catch (AxelorException e) {
//          e.printStackTrace();
//        }
      }
      event.setTotalEntry(event.getTotalEntry() + 1);
    }
    return bean;
  }
  
}
