package com.edunge.hospitalMgmt.repository;

import com.edunge.hospitalMgmt.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByPatientNumber(String patientNumber);
    List<Patient> findByCreatedAtGreaterThan(Date dateCreated);
    @Query(nativeQuery = true, value="SELECT * FROM patient a WHERE created_at BETWEEN ? and ?")
    List<Patient> findByDateRange(Date dateFrom, Date dateTo);
}
