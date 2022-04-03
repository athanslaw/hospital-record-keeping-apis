package com.edunge.hospitalMgmt.service.impl;

import com.edunge.hospitalMgmt.model.Patient;
import com.edunge.hospitalMgmt.repository.PatientRepository;
import com.edunge.hospitalMgmt.service.DownloadService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class DownloadServiceImpl implements DownloadService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadService.class);
    private final PatientRepository patientRepository;

    public DownloadServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public static class PatientDownload{
        private String patientNumber;
        private String patientName;
        private long patientAge;
        private LocalDateTime lastVisitDate;

        public String getPatientNumber() {
            return patientNumber;
        }

        public void setPatientNumber(String patientNumber) {
            this.patientNumber = patientNumber;
        }

        public String getPatientName() {
            return patientName;
        }

        public void setPatientName(String patientName) {
            this.patientName = patientName;
        }

        public long getPatientAge() {
            return patientAge;
        }

        public void setPatientAge(long patientAge) {
            this.patientAge = patientAge;
        }

        public LocalDateTime getLastVisitDate() {
            return lastVisitDate;
        }

        public void setLastVisitDate(LocalDateTime lastVisitDate) {
            this.lastVisitDate = lastVisitDate;
        }
    }

    @Override
    public PatientDownload getPatientRecords(String patientRecordNumber){
        Optional<Patient> patientRecord = patientRepository.findByPatientNumber(patientRecordNumber);
        PatientDownload patientDownload = new PatientDownload();
        if(patientRecord.isPresent()) {
            Patient patient = patientRecord.get();
            patientDownload.setPatientName(patient.getName());
            patientDownload.setPatientNumber(patient.getPatientNumber());
            patientDownload.setPatientAge(patient.getAge());
            patientDownload.setLastVisitDate(Instant.ofEpochMilli(patient.getLastVisitDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        return patientDownload;
    }

    public static void writeResults(PrintWriter writer, PatientDownload patient){
        try{
            ColumnPositionMappingStrategy<PatientDownload> mappingStrategy = new ColumnPositionMappingStrategy<>();
            mappingStrategy.setType(PatientDownload.class);

            String[] columns = new String[]{"patientNumber", "patientName", "patientAge", "lastVisitDate"};
            mappingStrategy.setColumnMapping(columns);
            mappingStrategy.generateHeader();
            String headerFromArray = Arrays.toString(columns).substring(1,Arrays.toString(columns).length()-1);
            writer.println(headerFromArray);
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withMappingStrategy(mappingStrategy)
                    .withSeparator(',')
                    .build();
            beanToCsv.write(patient);
        }
        catch (CsvException ex){
            LOGGER.error("Error mapping CSV", ex);
        }
    }

}
