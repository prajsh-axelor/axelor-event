package com.axelor.apps.event.service;

import com.axelor.meta.db.MetaFile;

public interface ImportEventRegistrationService {
  public void importEventReg(Integer eventId, MetaFile dataFile);
}
