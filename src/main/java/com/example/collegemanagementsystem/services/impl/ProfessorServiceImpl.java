package com.example.collegemanagementsystem.services.impl;


import com.example.collegemanagementsystem.dtos.ProfessorDto;
import com.example.collegemanagementsystem.dtos.StudentDto;
import com.example.collegemanagementsystem.dtos.SubjectDto;
import com.example.collegemanagementsystem.entities.Professor;
import com.example.collegemanagementsystem.entities.Student;
import com.example.collegemanagementsystem.entities.Subject;
import com.example.collegemanagementsystem.exception.ResourceNotFoundException;
import com.example.collegemanagementsystem.repositories.ProfessorRepository;
import com.example.collegemanagementsystem.repositories.StudentRepository;
import com.example.collegemanagementsystem.repositories.SubjectRepository;
import com.example.collegemanagementsystem.services.ProfessorService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    private  final ProfessorRepository professorRepository;
    private  final ModelMapper modelMapper;

    private final SubjectRepository subjectRepository;

    private final StudentRepository studentRepository;

    public ProfessorServiceImpl(ProfessorRepository professorRepository, ModelMapper modelMapper, SubjectRepository subjectRepository, StudentRepository studentRepository) {
        this.professorRepository = professorRepository;
        this.modelMapper = modelMapper;
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
    }


    @Override
    public ProfessorDto createProfessor(ProfessorDto professorDto) {

        Professor professor = modelMapper.map(professorDto, Professor.class);
        Professor savedProfessor = professorRepository.save(professor);
        return modelMapper.map(savedProfessor,ProfessorDto.class);
    }

    @Override
    public ProfessorDto getProfessorById(Long professorId) {

        isExistsByProfessorId(professorId);
        Professor professor = professorRepository.findById(professorId).get();

        return ProfessorDto
                .builder()
                .id(professor.getId())
                .name(professor.getName())
                .students(buildStudentDto(professor.getStudents()))
                .subjects(buildSubjectDto(professor.getSubjects()))
                .build();

    }

    private List<StudentDto> buildStudentDto(Set<Student> studentList){
        List<StudentDto> studentDtoList =new ArrayList<>();
        if(!studentList.isEmpty()){
            studentList.stream().map(student ->
                    studentDtoList.add(StudentDto
                            .builder()
                                    .id(student.getId())
                                    .name(student.getName())
                            .build()))
                    .collect(Collectors.toList());
        }
        return studentDtoList;
    }

    private Set<SubjectDto> buildSubjectDto(Set<Subject> subjectSet){
        Set<SubjectDto> subjectDtoSet =new HashSet<>();
        if(!subjectSet.isEmpty()){
            subjectSet.stream().map(subject ->
                    subjectDtoSet.add(SubjectDto.builder()
                                    .id(subject.getId())
                                    .name(subject.getName())
                            .build())).collect(Collectors.toList());
        }
        return subjectDtoSet;
    }

    @Override
    public ProfessorDto assignSubjectToProfessor(Long professorId, Long subjectId) {
        isExistsByProfessorId(professorId);
        isExistsBySubjectId(subjectId);
        Professor professor = professorRepository.findById(professorId).get();
        Subject subject = subjectRepository.findById(subjectId).get();

        subject.setCollegeProfessor(professor);
        subjectRepository.save(subject);
        professor.getSubjects().add(subject);

        return ProfessorDto.builder()
                .id(professor.getId())
                .name(professor.getName())
                .students(buildStudentDto(professor.getStudents()))
                .subjects(buildSubjectDto(professor.getSubjects()))
                .build();
    }

    @Override
    public ProfessorDto assignProfessorToStudent(Long professorId, Long studentId) {
        isExistsByProfessorId(professorId);
        isExistsByStudentId(studentId);
        Professor professor = professorRepository.findById(professorId).get();
        Student student = studentRepository.findById(studentId).get();
        student.getProfessors().add(professor);
        studentRepository.save(student);
        professor.getStudents().add(student);
        return ProfessorDto.builder()
                .id(professor.getId())
                .name(professor.getName())
                .students(buildStudentDto(professor.getStudents()))
                .subjects(buildSubjectDto(professor.getSubjects()))
                .build();
    }


    public void isExistsByProfessorId(Long professorId) {
        boolean exists = professorRepository.existsById(professorId);
        if (!exists) {
            throw new ResourceNotFoundException("Professor not found with id :" + professorId);
        }
    }

    public void isExistsBySubjectId(Long subjectId) {
        boolean exists = subjectRepository.existsById(subjectId);
        if (!exists) {
            throw new ResourceNotFoundException("Subject not found with id :" + subjectId);
        }
    }

    public void isExistsByStudentId(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new ResourceNotFoundException("Student not found with id :" + studentId);
        }
    }

}
