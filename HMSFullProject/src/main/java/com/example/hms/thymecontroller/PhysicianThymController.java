package com.example.hms.thymecontroller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
 
import com.example.hms.exception.PhysicianIdNotFound;
import com.example.hms.model.Physician;
import com.example.hms.service.HMSPhysicianService;
 
import java.util.List;
import java.util.Map;
 
/**
* Thymeleaf Controller for managing physician-related operations.
*/
@Controller
public class PhysicianThymController {
 
	@Autowired HMSPhysicianService phyService;
 
	@GetMapping("/physician/home")
	public String home() {
		return "physician/physician-home";
	}
 
	@GetMapping("/physician/home/form")
	public String getFormId() {
		return "physician/form-for-id";
	}
 
	@GetMapping("/physician/empid")
	public String getPhysicianById(@RequestParam("empid") int empid, Model model) {
	    try {
	        Physician physician = phyService.getPhyById(empid);
	        model.addAttribute("physician", physician);
	        return "physician/get-physician-id";
	    } catch (PhysicianIdNotFound e) {
	        return "physician/physician-id-not-found";
	    }
	}
 
	@GetMapping("/physician/form-name")
	public String showNameForm() {
		return "physician/form-for-name";  // This shows the form HTML page
	}
 
	@GetMapping("/physician/name")
	public String getPhysicianByName(@RequestParam("name") String name, Model model) {
		Physician physician = phyService.getPhyByName(name);
		if (physician == null) {
			return "physician/physician-id-not-found";
		} else {
			model.addAttribute("physician", physician);
			return "physician/get-physician-name";
		}
	}
	@GetMapping("/physician/form-position")
	public String showPositionForm() {
		return "physician/form-for-position";
	}
 
	@GetMapping("/physician/position")
	public String getPhysicianByPosition(@RequestParam("position") String position, Model model) {
		List<Physician> physicians = phyService.getPhyByPos(position);
		if (physicians.size() == 0) {
			return "physician/physician-id-not-found";
		} else {
			model.addAttribute("physicians", physicians);
			return "physician/get-physician-position";
		}
	}
 
	@GetMapping("/physician/all")
	public String getAllPhysicians(Model model) {
		List<Physician> physicians = phyService.getAll();
		model.addAttribute("physicians", physicians);
		return "physician/get-physician-all";
	}
 
	@GetMapping("/physician/new")
	public String showNewPhysicianForm() {
		return "physician/post-new-physician";  // This shows the form HTML page
	}
 
	// Handle posting a new physician 
	@PostMapping("/physician/new")
	public String postNewPhysician(@RequestParam("name") String name,
	                               @RequestParam("employeeID") Integer employeeID,
	                               @RequestParam("position") String position,
	                               @RequestParam("ssn") Integer ssn,
	                               Model model) {
	    // Create a new Physician object
	    Physician physician = new Physician();
	    physician.setName(name);
	    physician.setEmployeeID(employeeID);
	    physician.setPosition(position);
	    physician.setSsn(ssn);
 
	    // Save the physician using the service
	    phyService.savePhysician(physician);
 
	    // Add the newly added physician to the model
	    model.addAttribute("physician", physician);
 
	    // Redirect to the success page with the physician details
	    return "physician/post-physician-success";  // This renders the success HTML page
	}
	@GetMapping("/physician/update/name")
	public String showUpdatePhysicianNameForm() {
		return "physician/form-update-name";
	}
	@PostMapping("/physician/updatesuccess/name")
	public String updatePhysicianName(@RequestParam("empid") Integer empid, @RequestParam("newName") String newName, Model model) {
		Physician updatedPhysician = phyService.updateName(empid, newName);
		model.addAttribute("physician", updatedPhysician);
		return "physician/update-physician-name-success";
	}
 
	@GetMapping("/physician/update/ssn")
	public String showUpdatePhysicianSSNForm() {
		return "physician/form-update-ssn";
	}
 
	@PostMapping("/physician/updatesuccess/ssn")
	public String updatePhysicianSSN(@RequestParam("empid") Integer empid, @RequestParam("newSSN") Integer newSSN, Model model) {
		Physician updatedPhysician = phyService.updateSSN(empid, newSSN);
		model.addAttribute("physician", updatedPhysician);
		return "physician/update-physician-ssn-success";
	}
 
	@GetMapping("/physician/update/position")
	public String showPhysicianPositionForm() {
		return "physician/form-update-position";
	}
 
	@PostMapping("/physician/updatesuccess/position")
	public String updatePhysicianPosition(@RequestParam("empid") Integer empid, @RequestParam("position") String position, Model model) {
		Physician updatedPhysician = phyService.updatePosition(position, empid);
		model.addAttribute("physician", updatedPhysician);
		return "physician/update-physician-position-success";
	}
 
}