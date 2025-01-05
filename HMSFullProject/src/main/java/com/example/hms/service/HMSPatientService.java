package com.example.hms.service;
import java.util.List;

import com.example.hms.model.Patient;
import com.example.hms.model.Physician;
 

 
public interface HMSPatientService {
	List<Patient> getAllPatients();
	List<Patient> getPatientsByPhysicianId(int physicianId);
	Patient getPatientByPhysicianIdAndPatientId(int physicianId, int patientId);
	int getPatientInsuranceByPatientId(int patientId);
	List<Physician> getPhysiciansByPatientId(int patientId);
	Patient addPatient(Patient patient);
	boolean updateAddress(int ssn, String address);
	String updatePhone(int ssn, String newPhone);
}
