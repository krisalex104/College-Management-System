package com.example.collegemanagementsystem.controllers;

import com.example.collegemanagementsystem.dtos.StudentDto;
import com.example.collegemanagementsystem.entities.Student;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/audit")
public class AuditController {

    private final EntityManagerFactory entityManagerFactory;

    public AuditController(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @GetMapping("/student/{studentId}")
    ResponseEntity<List<StudentDto>>  getStudentRevisions(@PathVariable Long studentId){
        AuditReader auditReader = AuditReaderFactory.get(entityManagerFactory.createEntityManager());
        List<Number> revisions = auditReader.getRevisions(Student.class, studentId);

        List<StudentDto> studentDtoList = revisions
                .stream()
                .map(revisionNumber -> buildStudentDto(auditReader.find(Student.class, studentId, revisionNumber)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(studentDtoList);

    }

    private StudentDto buildStudentDto(Student student){
        return StudentDto.builder()
                .id(student.getId())
                .name(student.getName())
                .createdBy(student.getCreatedBy())
                .updatedBy(student.getUpdatedBy())
                .createdDate(student.getCreatedDate())
                .updatedDate(student.getUpdatedDate())
                .build();
    }
}
