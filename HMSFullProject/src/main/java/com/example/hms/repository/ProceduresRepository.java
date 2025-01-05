package com.example.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hms.model.Procedures;



@Repository
public interface ProceduresRepository extends JpaRepository<Procedures, Integer>{
	public Procedures findByName(String name);
}
