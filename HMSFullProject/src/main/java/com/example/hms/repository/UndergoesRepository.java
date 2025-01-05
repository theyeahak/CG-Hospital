package com.example.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hms.model.Undergoes;
import com.example.hms.model.UndergoesId;

@Repository
public interface UndergoesRepository extends JpaRepository<Undergoes, UndergoesId> {
	
}
