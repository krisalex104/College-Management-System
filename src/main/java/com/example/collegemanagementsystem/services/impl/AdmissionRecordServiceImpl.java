package com.example.collegemanagementsystem.services.impl;


import com.example.collegemanagementsystem.dtos.AdmissionRecordDto;
import com.example.collegemanagementsystem.dtos.StudentDto;
import com.example.collegemanagementsystem.entities.AdmissionRecord;
import com.example.collegemanagementsystem.entities.Student;
import com.example.collegemanagementsystem.exception.ResourceNotFoundException;
import com.example.collegemanagementsystem.repositories.AdmissionRecordRepository;
import com.example.collegemanagementsystem.repositories.StudentRepository;
import com.example.collegemanagementsystem.services.AdmissionRecordService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AdmissionRecordServiceImpl implements AdmissionRecordService {

    private final AdmissionRecordRepository admissionRecordRepository;
    private final ModelMapper modelMapper;

    private final StudentRepository studentRepository;

    public AdmissionRecordServiceImpl(AdmissionRecordRepository admissionRecordRepository, ModelMapper modelMapper, StudentRepository studentRepository) {
        this.admissionRecordRepository = admissionRecordRepository;
        this.modelMapper = modelMapper;
        this.studentRepository = studentRepository;
    }

    @Override
    public AdmissionRecordDto createAdmissionRecord(AdmissionRecordDto admissionRecordDto) {

        AdmissionRecord admissionRecord = modelMapper.map(admissionRecordDto, AdmissionRecord.class);
        return modelMapper.map(admissionRecordRepository.save(admissionRecord),AdmissionRecordDto.class);
    }

    @Override
    public AdmissionRecordDto getAdmissionRecord(Long admissionRecordId) {
        isExistsByRecordId(admissionRecordId);

        AdmissionRecord admissionRecord = admissionRecordRepository.findById(admissionRecordId).get();

        return AdmissionRecordDto
                .builder()
                .id(admissionRecord.getId())
                .fees(admissionRecord.getFees())
                .studentDetails(buildStudentDto(admissionRecord.getStudentDetails()))
                .build();
    }

    private StudentDto buildStudentDto(Student student){
        StudentDto studentDto = null;
        if(Objects.nonNull(student)){
            studentDto = StudentDto
                    .builder()
                    .id(student.getId())
                    .name(student.getName())
                    .build();
        }

        return studentDto;
    }

    @Override
    public AdmissionRecordDto assignAdmissionRecordToStudent(Long admissionRecordId, Long studentId) {
        isExistsByRecordId(admissionRecordId);
        isExistsByStudentId(studentId);
        Optional<AdmissionRecord> optionalAdmissionRecord = admissionRecordRepository.findById(admissionRecordId);
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        AdmissionRecord admissionRecord = optionalAdmissionRecord.flatMap(admissionRecord1 ->
                optionalStudent.map(student -> {
                    admissionRecord1.setStudentDetails(student);
                    return admissionRecordRepository.save(admissionRecord1);
                })
        ).get();
        StudentDto studentDto = StudentDto
                .builder()
                .id(admissionRecord.getStudentDetails().getId())
                .name(admissionRecord.getStudentDetails().getName())
                .build();

        return AdmissionRecordDto
                .builder()
                .id(admissionRecord.getId())
                .fees(admissionRecord.getFees())
                .studentDetails(studentDto)
                .build();

    }

    public void isExistsByRecordId(Long recordId) {
        boolean exists = admissionRecordRepository.existsById(recordId);
        if (!exists) {
            throw new ResourceNotFoundException("Admission Record not found with id :" + recordId);
        }
    }

    public void isExistsByStudentId(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new ResourceNotFoundException("Student not found with id :" + studentId);
        }
    }
}
