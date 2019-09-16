package com.axelor.apps.event.module;

import com.axelor.app.AxelorModule;
import com.axelor.apps.event.service.DiscountService;
import com.axelor.apps.event.service.DiscountServiceImpl;
import com.axelor.apps.event.service.EventRegistrationService;
import com.axelor.apps.event.service.EventRegistrationServiceImpl;
import com.axelor.apps.event.service.EventService;
import com.axelor.apps.event.service.EventServiceImpl;
import com.axelor.apps.event.service.ImportEventRegistrationService;
import com.axelor.apps.event.service.ImportEventRegistrationServiceImpl;

public class EventMoudle extends AxelorModule {

  @Override
  protected void configure() {
    // TODO Auto-generated method stub
    bind(DiscountService.class).to(DiscountServiceImpl.class);
    bind(EventRegistrationService.class).to(EventRegistrationServiceImpl.class);
    bind(EventService.class).to(EventServiceImpl.class);
    bind(ImportEventRegistrationService.class).to(ImportEventRegistrationServiceImpl.class);
  }
}
