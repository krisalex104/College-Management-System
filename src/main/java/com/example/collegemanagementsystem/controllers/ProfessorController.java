package com.example.collegemanagementsystem.controllers;

import com.example.collegemanagementsystem.dtos.ProfessorDto;
import com.example.collegemanagementsystem.dtos.SubjectDto;
import com.example.collegemanagementsystem.services.ProfessorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

    private final ProfessorService professorService;


    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @PostMapping
    public ResponseEntity<ProfessorDto> createProfessor(@RequestBody ProfessorDto professorDto){
        ProfessorDto professor = professorService.createProfessor(professorDto);
        return  new ResponseEntity<>(professor, HttpStatus.CREATED);
    }

    @GetMapping("/{professorId}")
    public ResponseEntity<ProfessorDto> getProfessorById(@PathVariable Long professorId){
        ProfessorDto professor = professorService.getProfessorById(professorId);
        return ResponseEntity.ok(professor);
    }

    @PutMapping(path = "/{professorId}/subject/{subjectId}")
    public ResponseEntity<ProfessorDto> assignSubjectToProfessor(@PathVariable Long professorId,@PathVariable Long subjectId){
        ProfessorDto professorDto = professorService.assignSubjectToProfessor(professorId, subjectId);
        return ResponseEntity.ok(professorDto);

    }

    @PutMapping(path = "/{professorId}/student/{studentId}")
    public ResponseEntity<ProfessorDto> assignStudentToProfessor(@PathVariable Long professorId,@PathVariable Long studentId){
        ProfessorDto professorDto = professorService.assignProfessorToStudent(professorId, studentId);
        return ResponseEntity.ok(professorDto);

    }

}
