package com.edunge.hospitalMgmt.service.impl;

import com.edunge.hospitalMgmt.dto.DateRangeDto;
import com.edunge.hospitalMgmt.dto.PatientDto;
import com.edunge.hospitalMgmt.exceptions.NotFoundException;
import com.edunge.hospitalMgmt.model.Patient;
import com.edunge.hospitalMgmt.repository.PatientRepository;
import com.edunge.hospitalMgmt.repository.UserRepository;
import com.edunge.hospitalMgmt.response.PatientResponse;
import com.edunge.hospitalMgmt.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class PatientServiceImpl implements PatientService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(UserRepository userRepository, PatientRepository patientRepository) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
    }

    private String generatePatientNumber(){
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    @Override
    public PatientResponse savePatientRecord(PatientDto patientDto) {
        String patientNumber = this.generatePatientNumber();
        Optional<Patient> existingUser = patientRepository.findByPatientNumber(patientNumber);
        if(existingUser.isPresent()){
            patientNumber = this.generatePatientNumber();
        }
        patientDto.setPatientNumber(patientNumber);
        Patient patient = this.toPatient(patientDto);
        patientRepository.save(patient);
        return new PatientResponse("00", "Record saved successfully.", patient);
    }

    private Patient toPatient(PatientDto patientDto){
        Patient patient = new Patient();
        patient.setPatientNumber(patientDto.getPatientNumber());
        patient.setAge(patientDto.getAge());
        patient.setName(patientDto.getName());
        return patient;
    }

    private Date lastXYears(int x){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, x);
        return cal.getTime();
    }

    @Override
    public PatientResponse getPatientRecords(int periodInYears) throws NotFoundException {
        Date last2Years = this.lastXYears(-2);

        List<Patient> patients = patientRepository.findByCreatedAtGreaterThan(last2Years);
        return new PatientResponse("00", "Records Retrieved Successfully.", patients);
    }

    @Override
    public PatientResponse getPatientRecords(LocalDateTime dateFrom, LocalDateTime dateTo) throws NotFoundException {
        Date dateFrom1 = Date.from(dateFrom.atZone(ZoneId.systemDefault()).toInstant());
        Date dateTo1 = Date.from(dateTo.atZone(ZoneId.systemDefault()).toInstant());

        List<Patient> patients = patientRepository.findByDateRange(dateFrom1, dateTo1);
        return new PatientResponse("00", patients.size()+" patient(s) fetched successfully.", patients);
    }

    @Override
    public PatientResponse deletePatientRecords(LocalDateTime dateFrom, LocalDateTime dateTo) throws NotFoundException {
        List<Patient> patients = this.getPatientRecords(dateFrom, dateTo).patients;
        int count = patients.size();
        if(count > 0) {
            patients.forEach(patient -> patientRepository.deleteById(patient.getId()));
            return new PatientResponse("00", count + " patient record(s) deleted successfully.");
        }
        else{
            return new PatientResponse("00", "No record found within the  given date range successfully.");
        }
    }

}
