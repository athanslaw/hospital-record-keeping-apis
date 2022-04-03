package com.edunge.hospitalMgmt;

import com.edunge.hospitalMgmt.dto.PatientDto;
import com.edunge.hospitalMgmt.exceptions.NotFoundException;
import com.edunge.hospitalMgmt.response.PatientResponse;
import com.edunge.hospitalMgmt.service.PatientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes= HospitalMgmtApplication.class)
@ActiveProfiles("local")
public class PatientsTest {

    @Autowired
    PatientService patientService;
    private static final Logger LOGGER = LoggerFactory.getLogger(PatientsTest.class);
    private static final PatientDto patientDto = new PatientDto();

    @Test
    public void savePatientRecord() {
        patientDto.setAge(25);
        patientDto.setName("Amos Samuel");
        PatientResponse patients = patientService.savePatientRecord(patientDto);
        assert(patients.getStatusCode().equals("00"));
    }

    @Test
    public void fetchPatientProfileSince2Years() throws NotFoundException {
        PatientResponse patients = patientService.getPatientRecords(2);
        assert(patients.getStatusCode().equals("00"));
    }

    @Test
    public void fetchPatientProfileByDateRange() throws NotFoundException {
        Date dateTo = Date.from(Instant.now());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -2);
        Date dateFrom = cal.getTime();
        LocalDateTime dateFrom1 = Instant.ofEpochMilli(dateFrom.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime dateTo1 = Instant.ofEpochMilli(dateTo.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();

        PatientResponse patients = patientService.getPatientRecords(dateFrom1, dateTo1);
        assert(patients.getStatusCode().equals("00"));
    }

    @Test
    public void deletePatientProfileByDateRange() throws NotFoundException {
        Date dateTo = Date.from(Instant.now());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -2);
        Date dateFrom = cal.getTime();
        LocalDateTime dateFrom1 = Instant.ofEpochMilli(dateFrom.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime dateTo1 = Instant.ofEpochMilli(dateTo.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();

        PatientResponse patients = patientService.deletePatientRecords(dateFrom1, dateTo1);
        assert(patients.getStatusCode().equals("00"));
    }

}
