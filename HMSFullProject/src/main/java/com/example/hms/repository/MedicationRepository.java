package com.example.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hms.model.Medication;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Integer>{
	
}
