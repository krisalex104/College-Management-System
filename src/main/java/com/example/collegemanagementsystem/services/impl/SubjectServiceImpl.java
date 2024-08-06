package com.example.collegemanagementsystem.services.impl;


import com.example.collegemanagementsystem.dtos.ProfessorDto;
import com.example.collegemanagementsystem.dtos.SubjectDto;
import com.example.collegemanagementsystem.entities.Professor;
import com.example.collegemanagementsystem.entities.Subject;
import com.example.collegemanagementsystem.exception.ResourceNotFoundException;
import com.example.collegemanagementsystem.repositories.SubjectRepository;
import com.example.collegemanagementsystem.services.SubjectService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    private final ModelMapper modelMapper;

    public SubjectServiceImpl(SubjectRepository subjectRepository, ModelMapper modelMapper) {
        this.subjectRepository = subjectRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SubjectDto createSubject(SubjectDto subjectDto) {

        Subject subject = modelMapper.map(subjectDto, Subject.class);
        Subject saveSubject = subjectRepository.save(subject);
        return modelMapper.map(saveSubject,SubjectDto.class);
    }

    @Override
    public SubjectDto getSubjectById(Long subjectId) {
        isExistsBySubjectId(subjectId);
        Subject subject = subjectRepository.findById(subjectId).get();

        return SubjectDto.builder()
                .id(subject.getId())
                .name(subject.getName())
                .collegeProfessor(buildProfessorDto(subject.getCollegeProfessor()))
                .build();
    }

    private ProfessorDto buildProfessorDto(Professor professor){
        ProfessorDto professorDto = null;
        if(Objects.nonNull(professor)){
            professorDto = ProfessorDto.builder()
                    .id(professor.getId())
                    .name(professor.getName())
                    .build();
        }
        return professorDto;
    }

    public void isExistsBySubjectId(Long departmentId) {
        boolean exists = subjectRepository.existsById(departmentId);
        if (!exists) {
            throw new ResourceNotFoundException("Subject not found with id :" + departmentId);
        }
    }
}
