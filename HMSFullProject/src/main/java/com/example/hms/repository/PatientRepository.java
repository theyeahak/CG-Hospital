package com.example.hms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hms.model.Patient;

import com.example.hms.model.Physician;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {


	List<Patient> findByPcp(Physician physician);

    Optional<Patient> findBySsn(int ssn);

    Optional<Patient> findById(int ssn);
 
}
