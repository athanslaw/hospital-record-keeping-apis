package com.edunge.hospitalMgmt.controller;

import com.edunge.hospitalMgmt.dto.DateRangeDto;
import com.edunge.hospitalMgmt.dto.PatientDto;
import com.edunge.hospitalMgmt.dto.UserDto;
import com.edunge.hospitalMgmt.exceptions.AuthException;
import com.edunge.hospitalMgmt.exceptions.BadRequestException;
import com.edunge.hospitalMgmt.exceptions.NotFoundException;
import com.edunge.hospitalMgmt.exceptions.UserException;
import com.edunge.hospitalMgmt.response.PatientResponse;
import com.edunge.hospitalMgmt.response.UserResponse;
import com.edunge.hospitalMgmt.service.DownloadService;
import com.edunge.hospitalMgmt.service.PatientService;
import com.edunge.hospitalMgmt.service.UserService;
import com.edunge.hospitalMgmt.service.ValidationService;
import com.edunge.hospitalMgmt.service.impl.DownloadServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
@CrossOrigin(maxAge = 3600)
public class PatientController {

    @Autowired
    PatientService patientService;

    @Autowired
    ValidationService validationService;

    @Autowired
    DownloadService downloadService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerPatient(@RequestHeader("uuid") String uuid, @RequestBody PatientDto patientDto) throws AuthException {
        try {
            validationService.validateUuid(uuid);
            return new ResponseEntity<>(patientService.savePatientRecord(patientDto), HttpStatus.CREATED);
        }catch (AuthException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value="/limit", method = RequestMethod.GET)
    @ApiOperation(value = "This method fetches all registered users. This can only be accessed by users with administrative privilege.")
    public ResponseEntity<?> getPatientsForTwoYears(@RequestHeader("uuid") String uuid) throws Exception {
        try {
            validationService.validateUuid(uuid);
            return ResponseEntity.ok(patientService.getPatientRecords(2));
        }catch (AuthException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value="/dateRange", method = RequestMethod.GET)
    public ResponseEntity<?> getPatientsBetweenDateRange(@RequestHeader("uuid") String uuid, @RequestParam String dateFrom, @RequestParam String dateTo) throws Exception {
        try {
            validationService.validateUuid(uuid);
            LocalDateTime dateFrom1 = LocalDateTime.parse(dateFrom);
            LocalDateTime dateTo1 = LocalDateTime.parse(dateTo);
            return ResponseEntity.ok(patientService.getPatientRecords(dateFrom1, dateTo1));
        } catch (AuthException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value="/dateRange", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePatientsBetweenDateRange(@RequestHeader("uuid") String uuid, @RequestParam String dateFrom, @RequestParam String dateTo) throws Exception {
        try {
            validationService.validateUuid(uuid);
            LocalDateTime dateFrom1 = LocalDateTime.parse(dateFrom);
            LocalDateTime dateTo1 = LocalDateTime.parse(dateTo);
            return ResponseEntity.ok(patientService.deletePatientRecords(dateFrom1, dateTo1));
        } catch (AuthException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/download/{patientNumber}")
    public ResponseEntity downloadResult(@RequestHeader("uuid") String uuid, @PathVariable String patientNumber, HttpServletResponse response) throws IOException {
        try {
            validationService.validateUuid(uuid);
            response.setHeader("Content-Disposition", "attachment; filename=patient_info.csv");
            response.setHeader("Content-Type", "application/octet-stream");
            DownloadServiceImpl.PatientDownload patientDownload = downloadService.getPatientRecords(patientNumber);
            DownloadServiceImpl.writeResults(response.getWriter(), patientDownload);
            return new ResponseEntity(HttpStatus.OK);
        } catch (AuthException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}
