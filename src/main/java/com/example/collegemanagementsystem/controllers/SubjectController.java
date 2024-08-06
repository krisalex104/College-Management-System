package com.example.collegemanagementsystem.controllers;

import com.example.collegemanagementsystem.dtos.SubjectDto;
import com.example.collegemanagementsystem.services.SubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    public ResponseEntity<SubjectDto> createSubject(@RequestBody SubjectDto subjectDto){
        SubjectDto subject = subjectService.createSubject(subjectDto);
        return  new ResponseEntity<>(subject, HttpStatus.CREATED);
    }

    @GetMapping("/{subjectId}")
    public ResponseEntity<SubjectDto> getSubjectById(@PathVariable Long subjectId){
        SubjectDto subject = subjectService.getSubjectById(subjectId);
        return ResponseEntity.ok(subject);
    }
}
