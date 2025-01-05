package com.example.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hms.model.Prescribes;
import com.example.hms.model.PrescribesId;

@Repository
public interface PrescribesRepository extends JpaRepository<Prescribes, PrescribesId> {

}
