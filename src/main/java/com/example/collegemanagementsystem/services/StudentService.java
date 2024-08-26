package com.example.collegemanagementsystem.services;

import com.example.collegemanagementsystem.dtos.StudentDto;

import java.util.List;

public interface StudentService {

     StudentDto createStudent(StudentDto studentDto);
     StudentDto fetchStudentById(Long studentId);

     StudentDto assignSubjectToStudent(Long subjectId,Long studentId);

     StudentDto updateStudentDetails(Long studentId,StudentDto studentDto);

     List<StudentDto> fetchAllStudents();
}
