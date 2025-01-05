package com.example.hms.thymecontroller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hms.service.HMSTrainedInService;
import com.example.hms.serviceImpl.HMSTrainedInServiceImpl;

@Controller
public class TrainedInControllerForThyme {
	
	@Autowired
	HMSTrainedInServiceImpl trainedInService;
	
	

    // Display the update certification expiry form
	@GetMapping("/updateCertificationExpiry")
	public String showUpdateCertificationExpiryForm(Model model) {
	    model.addAttribute("message", ""); // Initialize with an empty message
	    return "KtrainedIn/update-certification-expiry"; // Ensure this matches the template name
	}

    // Handle the form submission
	@PostMapping("/updateCertificationExpiry")
	public String updateCertificationExpiry(
	        @RequestParam("physicianid") int physicianid,
	        @RequestParam("procedureid") int procedureid,
	        @RequestParam("newCertificationExpiry") String newCertificationExpiry,
	        Model model) {

	    try {
	        // Use a more flexible DateTimeFormatter to handle both single- and two-digit hour formats
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd[ H:mm:ss][ HH:mm:ss]");
	        LocalDateTime expiryDate = LocalDateTime.parse(newCertificationExpiry, formatter);

	        boolean result = trainedInService.updateCertificationExpiry(physicianid, procedureid, expiryDate);
	        model.addAttribute("message", result ? "Certification expiry updated successfully" : "Update failed");
	    } catch (Exception e) {
	        model.addAttribute("message", "Invalid date format, please use YYYY-MM-DD HH:MM:SS");
	    }

	    return "KTrainedIn/update-certification-expiry";
	}

}
