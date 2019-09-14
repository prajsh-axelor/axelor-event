package com.axelor.apps.event.service;

import java.io.File;
import com.google.common.io.Files;

import wslite.soap.SOAP;

import com.axelor.meta.MetaFiles;
import com.axelor.meta.db.MetaFile;

public class ImportEventRegistrationServiceImpl implements ImportEventRegistrationService{

	@Override
	public void importEventReg(Integer eventId, MetaFile dataFile) {
		System.out.println(eventId+"	"+dataFile);
		
//		 File dataCsvFile = this.getDataCsvFile(dataFile);
//		 System.out.println(dataCsvFile);
		
	}
	
	private File getDataCsvFile(MetaFile dataFile) {

	    File csvFile = null;
	    try {

	    	Files.copy(MetaFiles.getPath(dataFile).toFile(), csvFile);

	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return csvFile;
	  }
	
}
