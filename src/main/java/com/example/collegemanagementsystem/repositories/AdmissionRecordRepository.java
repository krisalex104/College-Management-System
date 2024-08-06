package com.example.collegemanagementsystem.repositories;

import com.example.collegemanagementsystem.entities.AdmissionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdmissionRecordRepository extends JpaRepository<AdmissionRecord,Long> {
}
