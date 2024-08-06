package com.example.collegemanagementsystem.repositories;

import com.example.collegemanagementsystem.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject,Long> {
}
