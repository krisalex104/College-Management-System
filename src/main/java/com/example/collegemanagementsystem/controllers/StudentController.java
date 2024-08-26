package com.example.collegemanagementsystem.controllers;

import com.example.collegemanagementsystem.dtos.StudentDto;
import com.example.collegemanagementsystem.dtos.SubjectDto;
import com.example.collegemanagementsystem.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto studentDto) {
        StudentDto student = studentService.createStudent(studentDto);

        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDto> fetchStudentById(@PathVariable Long studentId) {

        StudentDto studentDto = studentService.fetchStudentById(studentId);
        return ResponseEntity.ok(studentDto);

    }

    @PutMapping(path = "/{studentId}/subject/{subjectId}")
    public ResponseEntity<StudentDto> assignSubjectToProfessor(@PathVariable Long subjectId, @PathVariable Long studentId) {
        StudentDto studentDto = studentService.assignSubjectToStudent(subjectId, studentId);
        return ResponseEntity.ok(studentDto);

    }

    @PutMapping(path = "/{studentId}")
    public ResponseEntity<StudentDto> updateStudentDetails(@RequestBody StudentDto studentDto, @PathVariable Long studentId) {
        StudentDto updateStudentDetails = studentService.updateStudentDetails(studentId, studentDto);
        return ResponseEntity.ok(updateStudentDetails);
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> fetchAllStudents() {
        List<StudentDto> studentDtos = studentService.fetchAllStudents();
        return ResponseEntity.ok(studentDtos);

    }

}
