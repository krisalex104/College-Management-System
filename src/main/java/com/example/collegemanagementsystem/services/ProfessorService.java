package com.example.collegemanagementsystem.services;

import com.example.collegemanagementsystem.dtos.ProfessorDto;
import com.example.collegemanagementsystem.dtos.SubjectDto;

public interface ProfessorService {

    ProfessorDto createProfessor(ProfessorDto professorDto);

    ProfessorDto getProfessorById(Long professorId);

    ProfessorDto assignSubjectToProfessor(Long professorId,Long subjectId);

    ProfessorDto assignProfessorToStudent(Long professorId,Long studentId);
}
