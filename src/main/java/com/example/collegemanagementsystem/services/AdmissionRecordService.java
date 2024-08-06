package com.example.collegemanagementsystem.services;

import com.example.collegemanagementsystem.dtos.AdmissionRecordDto;
import com.example.collegemanagementsystem.entities.AdmissionRecord;

public interface AdmissionRecordService {

    AdmissionRecordDto createAdmissionRecord(AdmissionRecordDto admissionRecordDto);
    AdmissionRecordDto getAdmissionRecord(Long admissionRecordId);
    AdmissionRecordDto assignAdmissionRecordToStudent(Long admissionRecordId,Long studentId);

}
