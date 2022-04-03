package com.edunge.hospitalMgmt.service;

import com.edunge.hospitalMgmt.service.impl.DownloadServiceImpl;

import java.util.List;

public interface DownloadService {
    DownloadServiceImpl.PatientDownload getPatientRecords(String patientRecordNumber);
}
