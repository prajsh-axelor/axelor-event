package com.axelor.apps.event.web;

import java.util.LinkedHashMap;

import com.axelor.apps.event.service.ImportEventRegistrationService;
import com.axelor.inject.Beans;
import com.axelor.meta.db.MetaFile;
import com.axelor.meta.db.repo.MetaFileRepository;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;

public class ImportEventRegistrationController {
	@Inject
	ImportEventRegistrationService impEventRegSer;
	public void importEventRegistration(ActionRequest request, ActionResponse response) {

	    LinkedHashMap<String, Object> map =
	        (LinkedHashMap<String, Object>) request.getContext().get("metaFile");
	    MetaFile dataFile =
	        Beans.get(MetaFileRepository.class).find(((Integer) map.get("id")).longValue());
	    if(!dataFile.getFileType().equals("text/csv")) {
	    	response.setError("upload a csv file");
	    }else {
	    	// a csv file is uploaded
	    	Integer eventId = (Integer) request.getContext().get("_id");
	    	impEventRegSer.importEventReg( eventId, dataFile );
	    	
	    }
	    System.out.println("welcome here");

	   
	  }

}
