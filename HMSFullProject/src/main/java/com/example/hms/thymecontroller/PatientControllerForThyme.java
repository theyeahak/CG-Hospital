package com.example.hms.thymecontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hms.exception.PatientIdNotFoundException;
import com.example.hms.model.Patient;
import com.example.hms.model.Physician;
import com.example.hms.repository.PatientRepository;
import com.example.hms.repository.PhysicianRepository;
import com.example.hms.service.HMSPatientService;
import com.example.hms.serviceImpl.HMSPatientServiceImpl;

@Controller
public class PatientControllerForThyme {
	
	  @Autowired
      private HMSPatientServiceImpl patientService;
	  
	  @Autowired
	  PatientRepository patientRepository;
	  
	  
		@Autowired
		PhysicianRepository physicianRepository;
		

	
	// Mapping for "Display All Patients"
	    @GetMapping("/allThyme")
	    public String displayAllPatients(Model model) {
	        // Retrieve all patients from the service
	        List<Patient> patients = patientService.getAllPatients();

	        // Add the list of patients to the model
	        model.addAttribute("patients", patients);

	        // Return the Thymeleaf view (HTML page)
	        return "Patient/patients"; // Ensure 'patients.html' is the name of the Thymeleaf template in your templates directory
	    }
	 
	// Display the form to enter Physician ID
	 @GetMapping("/form")
	    public String showPhysicianForm() {
	        return "Patient/physician-form";  // Thymeleaf template for the form
	    }

	
	        
	        // Thymeleaf template for displaying patients
	 @GetMapping("/search")
	 public String searchPatients(
	         @RequestParam("physicianId") int physicianId,
	         @RequestParam(value = "patientId", required = false) Integer patientId,
	         Model model) {

	     if (physicianId <= 0) {
	         model.addAttribute("errorMessage", "Invalid Physician ID.");
	         return "Patient/physician-patient-form"; // Show form with the error message
	     }

	     if (patientId != null) {
	         // Get patient by Physician ID and Patient ID
	         Patient patient = patientService.getPatientByPhysicianIdAndPatientId(physicianId, patientId);
	         if (patient != null) {
	             model.addAttribute("patient", patient);
	         } else {
	             model.addAttribute("errorMessage", "Patient not found.");
	         }
	     } else {
	         // Get patients by Physician ID
	         List<Patient> patients = patientService.getPatientsByPhysicianId(physicianId);
	         if (!patients.isEmpty()) {
	             model.addAttribute("patients", patients);
	         } else {
	             model.addAttribute("errorMessage", "No patients found for this physician.");
	         }
	     }

	     return "Patient/physician-patient-form"; // The combined Thymeleaf template
	 }

	        
	       
	    
	    @GetMapping("/insurance/form")
	    public String showInsuranceAndPhysiciansForm() {
	        return "Patient/insurance-form";  // Thymeleaf template for the form
	    }
	    

	    // Handle form submission to view only patient insurance
	    @GetMapping("/insurance/search")
	    public String searchInsurance(
	            @RequestParam("patientId") int patientId,
	            Model model) {
	        try {
	            // Try to get patient insurance by Patient ID
	            int insuranceId = patientService.getPatientInsuranceByPatientId(patientId);
	            model.addAttribute("insuranceId", insuranceId);
	            model.addAttribute("message", ""); // Empty message for success case
	        } catch (Exception e) {
	            // Add an error message in case of failure
	            model.addAttribute("message", "Error: Could not find insurance for the given patient ID.");
	        }
	        return "Patient/insurance-results"; // Thymeleaf template for the insurance results
	    }

	    
	    
	    @GetMapping("/appointment/physician/form")
	    public String showPhysicianSearchForm() {
	        return "Patient/physician-search-form";  // Thymeleaf template for the form
	    }

	    @GetMapping("/appointment/physician/search")
	    public String getPhysiciansByPatientId(
	            @RequestParam("patientId") int patientId,
	            Model model) {
	        try {
	            List<Physician> physicians = patientService.getPhysiciansByPatientId(patientId);
	            model.addAttribute("physicians", physicians);
	        } catch (PatientIdNotFoundException e) {
	            model.addAttribute("errorMessage", e.getMessage());
	        }
	        return "Patient/physician-search-results";  // Thymeleaf template for the results
	    }
	


	    
	 // Display the form to add a patient
	    @GetMapping("/addPatientForm")
	    public String showAddPatientForm(Model model) {
	        model.addAttribute("patient", new Patient());
	        return "Patient/add-patient";
	    }

	    // Handle the form submission to add a patient
	    @PostMapping("/addPatient")
	    public String addPatient(@ModelAttribute("patient") Patient patient, Model model) {
	        try {
	            patientService.addPatient(patient);
	            model.addAttribute("message", "Patient added successfully!");
	        } catch (Exception e) {
	            model.addAttribute("message", "Error: " + e.getMessage());
	        }
	        return "Patient/add-patient";  // Return to the form page with success/error message
	    }
	    

	    // Display the update address form
	    @GetMapping("/updatePatientAddress")
	    public String showUpdateAddressForm(Model model) {
	        model.addAttribute("patient", new Patient());
	        return "Patient/update-address"; // Renders the update-address.html form
	    }

	    // Handle the form submission and call the REST endpoint
	    @PostMapping("/updatePatientAddress")
	    public String updatePatientAddress(@RequestParam("ssn") int ssn, @RequestParam("address") String address, Model model) {
	        boolean isUpdated = patientService.updateAddress(ssn, address);
	        if (isUpdated) {
	            model.addAttribute("message", "Address updated successfully!");
	        } else {
	            model.addAttribute("message", "Error: Patient not found.");
	        }
	        return "Patient/update-address"; // Re-render the form with success/error message
	    }
	    
	 // Display the update phone form
	    @GetMapping("/updatePatientPhone")
	    public String showUpdatePhoneForm(Model model) {
	        model.addAttribute("patient", new Patient());
	        return "Patient/update-phone"; // Renders the update-phone.html form
	    }

	    // Handle the form submission and call the REST endpoint
	    @PostMapping("/updatePatientPhone")
	    public String updatePatientPhone(@RequestParam("ssn") Integer ssn, @RequestParam("phone") String phone, Model model) {
	        String response = patientService.updatePhone(ssn, phone);
	        model.addAttribute("message", response);
	        return "Patient/update-phone"; // Re-render the form with success/error message
	    }
	    
}	    
