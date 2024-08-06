package com.example.collegemanagementsystem.repositories;

import com.example.collegemanagementsystem.entities.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor,Long> {
}
