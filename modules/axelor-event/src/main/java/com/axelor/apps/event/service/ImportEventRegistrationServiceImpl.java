package com.axelor.apps.event.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axelor.data.Importer;
import com.axelor.data.Listener;
import com.axelor.data.csv.CSVImporter;
import com.axelor.db.Model;
import com.axelor.exception.AxelorException;
import com.axelor.meta.MetaFiles;
import com.axelor.meta.db.MetaFile;
import com.google.common.io.Files;

public class ImportEventRegistrationServiceImpl implements ImportEventRegistrationService {

  private Logger log = LoggerFactory.getLogger(getClass());

  @Override
  public void importEventReg(Integer eventId, MetaFile dataFile) {

    File configXmlFile = this.getConfigXmlFile();
    File dataCsvFile = this.getDataCsvFile(dataFile);
    Map<String, Object> importContext = new HashMap<String, Object>();
    importContext.put("_eventId", eventId);
    importEventRegistration(configXmlFile, dataCsvFile, importContext);
  }

  private void importEventRegistration(
      File configXmlFile, File dataCsvFile, Map<String, Object> importContext) {
    try {

      Importer importer =
          new CSVImporter(configXmlFile.getAbsolutePath(), "/home/axelor/.event-app/attachments/");

      final List<Model> records = new ArrayList<>();

      importer.setContext(importContext);

      importer.addListener(
          new Listener() {

            @Override
            public void imported(Integer total, Integer success) {
              log.info("Record (total): " + total);
              log.info("Record (success): " + success);
            }

            @Override
            public void imported(Model bean) {
              if (bean != null) {
                log.info("Bean saved : {}(id={})", bean.getClass().getSimpleName(), bean.getId());
                records.add(bean);
              }
            }

            @Override
            public void handle(Model bean, Exception e) {
              if (bean == null) {
                log.info("Capacity full for this event.");
              }
            }
          });

      importer.run();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private File getDataCsvFile(MetaFile dataFile) {

    File csvFile = new File("/home/axelor/.event-app/attachments/", "tempfile.csv");
    try {
      if (dataFile != null) {
        Files.copy(MetaFiles.getPath(dataFile).toFile(), csvFile);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return csvFile;
  }

  private File getConfigXmlFile() {

    File configFile = null;
    try {
      configFile = File.createTempFile("event-input-config", ".xml");

      InputStream bindFileInputStream =
          this.getClass().getResourceAsStream("/demo/event-reg-config.xml");

      if (bindFileInputStream == null) {
        throw new AxelorException();
      }

      FileOutputStream outputStream = new FileOutputStream(configFile);

      IOUtils.copy(bindFileInputStream, outputStream);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return configFile;
  }
}
