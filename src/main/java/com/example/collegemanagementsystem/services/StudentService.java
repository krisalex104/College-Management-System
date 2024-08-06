package com.example.collegemanagementsystem.services;

import com.example.collegemanagementsystem.dtos.StudentDto;

public interface StudentService {

     StudentDto createStudent(StudentDto studentDto);
     StudentDto fetchStudentById(Long studentId);

     StudentDto assignSubjectToStudent(Long subjectId,Long studentId);
}
