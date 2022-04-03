package com.edunge.hospitalMgmt.response;

import com.edunge.hospitalMgmt.model.Patient;
import com.edunge.hospitalMgmt.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientResponse extends BaseResponse {
    private Patient patient;
    private String patientNumber;
    public List<Patient> patients;

    public PatientResponse(String code, String message) {
        super(code, message);
    }

    public PatientResponse(String code, String message, String patientNumber) {
        super(code, message);
        this.patientNumber = patientNumber;
    }

    public PatientResponse(String code, String message, Patient patient) {
        super(code,message);
        this.patient = patient;
    }

    public PatientResponse(String code, String message, List<Patient> patients) {
        super(code, message);
        this.patients = patients;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getPatientNumber() {
        return patientNumber;
    }

    public void setPatientNumber(String patientNumber) {
        this.patientNumber = patientNumber;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}
