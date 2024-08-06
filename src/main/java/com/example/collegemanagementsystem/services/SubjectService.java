package com.example.collegemanagementsystem.services;


import com.example.collegemanagementsystem.dtos.SubjectDto;
import com.example.collegemanagementsystem.entities.Subject;

public interface SubjectService {

    SubjectDto createSubject(SubjectDto subjectDto);

    SubjectDto getSubjectById(Long subjectId);
}
