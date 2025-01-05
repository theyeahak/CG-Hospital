package com.example.hms.service;

import java.time.LocalDateTime;
import java.util.List;


import com.example.hms.model.Physician;
import com.example.hms.model.Procedures;
import com.example.hms.model.Trained_In;



public interface HMSTrainedInService 
{
	public List<Procedures> getTreatmentsByPhysician(Integer physicianId);
	public List<Physician> getPhysicianByTreatment(Integer treatmentCode);
	public List<Procedures> getCertifiedProcedures();
	public Trained_In saveTrainedIn(Trained_In trainedIn);
	public List<Trained_In> findAll();
	public List<Procedures> getProceduresWithExpiringCertifications(Integer physicianId);
	 public Boolean updateCertificationExpiry(int physicianid, int procedureid, LocalDateTime newCertificationExpiry);
}
