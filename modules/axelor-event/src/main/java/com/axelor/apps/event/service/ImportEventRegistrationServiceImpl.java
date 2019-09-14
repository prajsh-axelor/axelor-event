package com.axelor.apps.event.service;

import java.io.File;
import java.io.FileReader;
import java.util.List;

import com.axelor.apps.event.db.Event;
import com.axelor.apps.event.db.EventRegistration;
import com.axelor.apps.event.db.repo.EventRegistrationRepository;
import com.axelor.apps.event.db.repo.EventRepository;
import com.axelor.data.csv.CSVImporter;
import com.axelor.inject.Beans;
import com.axelor.meta.MetaFiles;
import com.axelor.meta.db.MetaFile;
import com.google.common.io.Files;
import com.google.inject.persist.Transactional;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class ImportEventRegistrationServiceImpl implements ImportEventRegistrationService{

	@Override
	@Transactional
	public void importEventReg(Integer eventId, MetaFile dataFile) {
		System.out.println(eventId+"	"+dataFile);
		
		 File dataCsvFile = this.getDataCsvFile(dataFile);
		 
		 Event event = Beans.get(EventRepository.class).find((long)eventId);
		 List<EventRegistration> eventRegistrationsList = event.getEventRegistrationList();
		 EventRegistrationRepository eventRegistrationRepository = Beans.get(EventRegistrationRepository.class);
		 try {
		 
		 FileReader filereader = new FileReader(dataCsvFile); 
		  
	        // create csvReader object and skip first Line 
	        CSVReader csvReader = new CSVReaderBuilder(filereader) 
	                                  .withSkipLines(1) 
	                                  .build(); 
//	        CSVImporter
	        List<String[]> allData = csvReader.readAll(); 
	  
	        // print Data 
	        for (String[] row : allData) {

		        	EventRegistration eventRegistration = new EventRegistration();
		        	eventRegistration.setName(row[0]);
		        	eventRegistration.setEmail(row[1]);
		        	eventRegistration.setEvent(event);
		        	eventRegistrationsList.add(eventRegistration);
		        	eventRegistrationRepository.save(eventRegistration);
		        	
	         } 
	        
	      
	        Beans.get(EventRepository.class).save(event);
	    } 
	    catch (Exception e) { 
	        e.printStackTrace(); 
	    } 
		 
		
		 
		 System.out.println(event);
		 System.out.println("data csv file "+dataCsvFile);
		
	}
	
	private File getDataCsvFile(MetaFile dataFile) {

	    File csvFile = new File("/home/axelor/.event-app/attachments/","tempfile.csv");
	    try {
	    	if(dataFile != null) {
	    		Files.copy(MetaFiles.getPath(dataFile).toFile(), csvFile);
	    	}
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return csvFile;
	  }
	
}
