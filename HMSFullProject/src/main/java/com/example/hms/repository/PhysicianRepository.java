package com.example.hms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hms.model.Physician;


@Repository
public interface PhysicianRepository extends JpaRepository<Physician, Integer> {
	public Physician findByName(String name);
	public List<Physician> findByPosition(String pos);
}
