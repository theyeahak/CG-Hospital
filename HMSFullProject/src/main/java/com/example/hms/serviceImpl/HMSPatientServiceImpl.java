package com.example.hms.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hms.service.HMSPatientService;
import com.example.hms.model.Patient;
import com.example.hms.model.Physician;
import com.example.hms.repository.PatientRepository;
import com.example.hms.repository.PhysicianRepository;
import com.example.hms.exception.PhysicianIdNotFoundException;
import com.example.hms.exception.PatientIdNotFoundException;

import lombok.NoArgsConstructor;
 
/**
 * Service implementation for managing patient-related operations in the Hospital Management System.
 */
@Service
@NoArgsConstructor
public class HMSPatientServiceImpl implements HMSPatientService {

    private static final Logger logger = LoggerFactory.getLogger(HMSPatientServiceImpl.class);

    @Autowired 
    PatientRepository patientRepository;
    
    @Autowired
    PhysicianRepository physicianRepository;
    
    /**
     * Retrieves a list of all patients from the database.
     *
     * @return A list of all patients in the system.
     */
      @Override
	  public List<Patient> getAllPatients() {
    	  logger.info("Fetching all patients");
	       return patientRepository.findAll();
	    }
      
      /**
       * Retrieves a list of patients associated with a specific physician by their ID.
       * If the physician is not found, a custom exception is thrown.
       *
       * @param physicianId The ID of the physician.
       * @return A list of patients assigned to the specified physician.
       * @throws PhysicianIdNotFoundException If no physician with the provided ID is found.
       */
      
	 @Override
	  public List<Patient> getPatientsByPhysicianId(int physicianId) {
		 logger.info("Fetching patients for physician with ID: {}", physicianId);
		   Physician physician = physicianRepository.findById(physicianId).orElse(null);
		   if (physician == null) throw new PhysicianIdNotFoundException(physicianId + " Physician not found!");
		   return patientRepository.findByPcp(physician);
		}
	 
	 /**
	     * Retrieves a specific patient by their ID and the ID of their assigned physician.
	     * If the physician or patient is not found, or if the patient is not assigned to the specified physician, returns null.
	     *
	     * @param physicianId The ID of the physician.
	     * @param patientId The ID of the patient.
	     * @return The patient if they exist and are assigned to the specified physician; otherwise, null.
	     */
	 
	 @Override
	  public Patient getPatientByPhysicianIdAndPatientId(int physicianId, int patientId) {
		  logger.info("Fetching patient with ID: {} for physician with ID: {}", patientId, physicianId);
	       Physician physician = physicianRepository.findById(physicianId).orElse(null);
	       if (physician == null) {
	            return null;
	       }
 
	       Patient patient = patientRepository.findById(patientId).orElse(null);
	       if (patient != null && patient.getPcp().equals(physician)) {
	            return patient;
	       } else {
	            return null;
	       }
	    }
	 
	 /**
	     * Retrieves the insurance ID of a specific patient by their patient ID.
	     * If the patient is not found, a custom exception is thrown.
	     *
	     * @param patientId The ID of the patient.
	     * @return The insurance ID of the patient.
	     * @throws PatientIdNotFoundException If no patient with the provided ID is found.
	     */
	 
	   @Override	
	   public int getPatientInsuranceByPatientId(int patientId) {
		      Patient patient = patientRepository.findById(patientId).orElse(null);
		      if (patient == null) throw new PatientIdNotFoundException(patientId + " Patient not found");
		      return patient.getInsuranceID();
		   }
	   

    
    

    /**
     * Fetches all physicians associated with a specific patient.
     * 
     * @param patientId ID of the patient.
     * @return List of physicians associated with the patient.
     * @throws PatientIdNotFoundException if the patient ID is not found.
     */
    @Override   
    public List<Physician> getPhysiciansByPatientId(int patientId) {
        logger.info("Fetching physicians for patientId: {}", patientId);
        Patient patient = patientRepository.findById(patientId).orElse(null);
        if (patient == null) {
            logger.error("Patient not found with id: {}", patientId);
            throw new PatientIdNotFoundException(patientId + " Patient not found");
        }

        // Assuming a Patient is associated with multiple physicians via appointments
        return patient.getAppointments().stream()
            .map(appointment -> appointment.getPhysician())
            .distinct()
            .collect(Collectors.toList());
    }

    /**
     * Adds a new patient to the repository.
     * 
     * @param patient Patient details to be added.
     * @return Added patient details.
     */
    @Override
    public Patient addPatient(Patient patient) {
        logger.info("Adding a new patient: {}", patient);
        return patientRepository.save(patient);
    }

    /**
     * Updates the address of a patient based on their SSN.
     * 
     * @param ssn Social Security Number of the patient.
     * @param address New address to be updated.
     * @return True if the update is successful, false otherwise.
     */
    @Override
    public boolean updateAddress(int ssn, String address) {
        logger.info("Updating address for patient with SSN: {}", ssn);
        return patientRepository.findById(ssn)
            .map(patient -> {
                logger.info("Patient found with SSN: {}, updating address", ssn);
                patient.setAddress(address);
                patientRepository.save(patient);
                return true;
            }).orElseGet(() -> {
                logger.warn("Patient not found with SSN: {}", ssn);
                return false;
            });
    }

    /**
     * Updates the phone number of a patient based on their SSN.
     * 
     * @param ssn Social Security Number of the patient.
     * @param newPhone New phone number to be updated.
     * @return Status message indicating success or failure.
     */
    @Override
    public String updatePhone(int ssn, String newPhone) {
        logger.info("Updating phone for patient with SSN: {}", ssn);
        Optional<Patient> optionalPatient = patientRepository.findBySsn(ssn);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            logger.info("Patient found with SSN: {}, updating phone", ssn);
            patient.setPhone(newPhone);
            patientRepository.save(patient);
            return "Phone updated successfully";
        } else {
            logger.warn("Patient not found with SSN: {}", ssn);
            return "Patient not found";
        }
    }
}