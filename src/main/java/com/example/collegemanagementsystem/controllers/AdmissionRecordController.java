package com.example.collegemanagementsystem.controllers;

import com.example.collegemanagementsystem.dtos.AdmissionRecordDto;
import com.example.collegemanagementsystem.dtos.StudentDto;
import com.example.collegemanagementsystem.services.AdmissionRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admission/record")
public class AdmissionRecordController {

    private final AdmissionRecordService admissionRecordService;

    public AdmissionRecordController(AdmissionRecordService admissionRecordService) {
        this.admissionRecordService = admissionRecordService;
    }

    @PostMapping
    public ResponseEntity<AdmissionRecordDto> createRecord(@RequestBody AdmissionRecordDto admissionRecordDto){

        AdmissionRecordDto admissionRecord = admissionRecordService.createAdmissionRecord(admissionRecordDto);
        return new ResponseEntity<>(admissionRecord,HttpStatus.CREATED);

    }

    @GetMapping("/{recordId}")
    public ResponseEntity<AdmissionRecordDto> fetchRecordById(@PathVariable Long recordId){
        AdmissionRecordDto admissionRecord = admissionRecordService.getAdmissionRecord(recordId);
        return ResponseEntity.ok(admissionRecord);

    }

    @PutMapping(path = "/{recordId}/student/{studentId}")
    public ResponseEntity<AdmissionRecordDto> assignRecordToStudent(@PathVariable Long recordId, @PathVariable Long studentId){
        AdmissionRecordDto admissionRecordDto = admissionRecordService.assignAdmissionRecordToStudent(recordId, studentId);
        return ResponseEntity.ok(admissionRecordDto);

    }
}
