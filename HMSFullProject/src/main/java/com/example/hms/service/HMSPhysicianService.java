package com.example.hms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.hms.exception.PhysicianIdNotFound;
import com.example.hms.model.Physician;
import com.example.hms.repository.PhysicianRepository;


public interface HMSPhysicianService {
	

	public Physician getPhyById(int empid);
	public List<Physician> getAll();
	public Physician getPhyByName(String name);
	public List<Physician> getPhyByPos(String pos);
	public void addPhysician(Physician p);
	public Physician updatePosition(String position, Integer employeeId);
	public Physician updateName(Integer empid, String newName);
	public Physician updateSSN(Integer empid, Integer newSSN);
	public void savePhysician(Physician physician);

	
}
