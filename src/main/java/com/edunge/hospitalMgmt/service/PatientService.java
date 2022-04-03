package com.edunge.hospitalMgmt.service;

import com.edunge.hospitalMgmt.dto.DateRangeDto;
import com.edunge.hospitalMgmt.dto.PatientDto;
import com.edunge.hospitalMgmt.exceptions.NotFoundException;
import com.edunge.hospitalMgmt.response.PatientResponse;

import java.time.LocalDateTime;
import java.util.Date;

public interface PatientService {
    PatientResponse savePatientRecord(PatientDto patientDto);
    PatientResponse getPatientRecords(int periodInYears) throws NotFoundException;
    PatientResponse getPatientRecords(LocalDateTime dateFrom, LocalDateTime dateTo) throws NotFoundException;
    PatientResponse deletePatientRecords(LocalDateTime dateFrom, LocalDateTime dateTo) throws NotFoundException;
}
