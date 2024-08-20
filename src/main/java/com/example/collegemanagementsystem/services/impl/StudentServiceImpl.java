package com.example.collegemanagementsystem.services.impl;

import com.example.collegemanagementsystem.dtos.ProfessorDto;
import com.example.collegemanagementsystem.dtos.StudentDto;
import com.example.collegemanagementsystem.dtos.SubjectDto;
import com.example.collegemanagementsystem.entities.Professor;
import com.example.collegemanagementsystem.entities.Student;
import com.example.collegemanagementsystem.entities.Subject;
import com.example.collegemanagementsystem.exception.ResourceNotFoundException;
import com.example.collegemanagementsystem.repositories.StudentRepository;
import com.example.collegemanagementsystem.repositories.SubjectRepository;
import com.example.collegemanagementsystem.services.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    private final SubjectRepository subjectRepository;

    public StudentServiceImpl(StudentRepository studentRepository, ModelMapper modelMapper, SubjectRepository subjectRepository) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public StudentDto createStudent(StudentDto studentDto) {

        Student student = modelMapper.map(studentDto, Student.class);
        Student savedStudent = studentRepository.save(student);
        return modelMapper.map(savedStudent,StudentDto.class);
    }

    @Override
    public StudentDto fetchStudentById(Long studentId) {
        isExistByStudentId(studentId);
        Student student = studentRepository.findById(studentId).get();

        return StudentDto.builder()
                .id(student.getId())
                .name(student.getName())
                .subjects(buildSubjectDto(student.getSubjects()))
                .professors(buildProfessorDto(student.getProfessors()))
                .build();
    }

    @Override
    public StudentDto assignSubjectToStudent(Long subjectId, Long studentId) {
        isExistByStudentId(studentId);
        isExistsBySubjectId(subjectId);
        Student student = studentRepository.findById(studentId).get();
        Subject subject = subjectRepository.findById(subjectId).get();
        student.getSubjects().add(subject);
        studentRepository.save(student);
        subject.getStudents().add(student);

        return StudentDto.builder()
                .id(student.getId())
                .name(student.getName())
                .subjects(buildSubjectDto(student.getSubjects()))
                .professors(buildProfessorDto(student.getProfessors()))
                .build();
    }

    @Override
    public StudentDto updateStudentDetails(Long studentId,StudentDto studentDto) {
        isExistByStudentId(studentId);

        Student student = studentRepository.findById(studentId).get();
        studentDto.setId(studentId);
        modelMapper.map(studentDto,student);
        Student updatedStudent = studentRepository.save(student);

        return StudentDto.builder()
                .id(updatedStudent.getId())
                .name(updatedStudent.getName())
                .subjects(buildSubjectDto(updatedStudent.getSubjects()))
                .professors(buildProfessorDto(updatedStudent.getProfessors()))
                .build();

    }


    private List<ProfessorDto> buildProfessorDto(Set<Professor> professorList){
        List<ProfessorDto> professorDtoList =new ArrayList<>();
        if(!professorList.isEmpty()){
            professorList.stream().map(student ->
                            professorDtoList.add(ProfessorDto
                                    .builder()
                                    .id(student.getId())
                                    .name(student.getName())
                                    .build()))
                    .collect(Collectors.toList());
        }
        return professorDtoList;
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


    private void isExistByStudentId(Long studentId){
        boolean exists = studentRepository.existsById(studentId);
        if(!exists){
            throw new ResourceNotFoundException("Student not found with id :" + studentId);
        }
    }

    public void isExistsBySubjectId(Long subjectId) {
        boolean exists = subjectRepository.existsById(subjectId);
        if (!exists) {
            throw new ResourceNotFoundException("Subject not found with id :" + subjectId);
        }
    }
}
