package com.example.hms.thymecontroller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hms.exception.NoNurseFoundByPatientIdException;
import com.example.hms.model.Appointment;
import com.example.hms.model.Nurse;
import com.example.hms.model.Physician;
import com.example.hms.service.HMSAppointmentService;
import com.example.hms.serviceImpl.HMSAppointmentServiceImpl;

@Controller
public class ThymControllerForAppointment {
	
	  @Autowired
	    private HMSAppointmentServiceImpl appointmentService;

	    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	    // Show the form for entering Patient ID and Date
	    @GetMapping("/physician-form")
	    public String showPhysicianForm() {
	        return "KAppointment/appointmentPhysician";  // Thymeleaf template for the form
	    }

	    // Process the form and display physicians
	    @GetMapping("/physicians")
	    public String getPhysicians(
	            @RequestParam("patientId") String patientId,
	            @RequestParam("date") String date,
	            Model model) {

	        // Parse the date string to LocalDateTime
	        LocalDateTime localDate;
	        try {
	            localDate = LocalDateTime.parse(date, DATE_TIME_FORMATTER);
	        } catch (Exception e) {
	            model.addAttribute("errorMessage", "Invalid date format. Please use 'yyyy-MM-dd HH:mm:ss'.");
	            return "KAppointment/appointmentPhysician";  // Return to form with error message
	        }

	        // Retrieve physicians from the service
	        List<Physician> physicians = appointmentService.getPhysicianByPatientIdAndDate(patientId, localDate);
	        model.addAttribute("physicians", physicians);

	        // Check if no physicians were found
	        if (physicians.isEmpty()) {
	            model.addAttribute("errorMessage", "No physicians found for the given Patient ID and Date.");
	        }

	        return "KAppointment/physiciansList";  // Thymeleaf template for displaying physicians
	    }
	    // Show the form for searching nurses
	    @GetMapping("/nurse-form")
	    public String showNurseForm() {
	        return "KAppointment/nursesForm";  // Thymeleaf template for the form
	    }

	    // Process the form and display nurses
	    @GetMapping("/nurses")
	    public String getNurses(
	            @RequestParam("patientId") String patientId,
	            @RequestParam("date") String date,
	            Model model) {

	        // Parse the date string to LocalDateTime
	        LocalDateTime localDate;
	        try {
	            localDate = LocalDateTime.parse(date, DATE_TIME_FORMATTER);
	        } catch (Exception e) {
	            model.addAttribute("errorMessage", "Invalid date format. Please use 'yyyy-MM-dd HH:mm:ss'.");
	            return "KAppointment/nursesForm";  // Return to form with error message
	        }

	        // Retrieve nurses from the service
	        List<Nurse> nurses = appointmentService.getNursesByPatientIdAndDate(patientId, localDate);
	        model.addAttribute("nurses", nurses);

	        // Check if no nurses were found
	        if (nurses.isEmpty()) {
	            model.addAttribute("errorMessage", "No nurses found for the given Patient ID and Date.");
	        }

	        return "KAppointment/nursesList";  // Thymeleaf template for displaying nurses
	    }
	    
	    @SuppressWarnings("removal")
	    @GetMapping("/appointments")
	    public String showAppointmentForm(Model model) {
	        model.addAttribute("patientId", new Integer(0)); // Placeholder for patientId input
	        model.addAttribute("message", ""); // Initialize message
	        return "KAppointment/appointment-form";
	    }

	    @PostMapping("/appointments")
	    public String getAppointments(@RequestParam("patientId") int patientId, Model model) {
	        try {
	            List<LocalDateTime> appointmentDates = appointmentService.getAppointmentDatesByPatientId(patientId);

	            if (appointmentDates.isEmpty()) {
	                model.addAttribute("message", "No appointments found for Patient ID: " + patientId);
	            } 
	            model.addAttribute("appointmentDates", appointmentDates);
	        } catch (Exception e) {
	            // Handle the exception and show error message
	            model.addAttribute("message", "An error occurred while retrieving appointments. Please try again.");
	            model.addAttribute("appointmentDates", Collections.emptyList()); // Ensure it's an empty list on error
	        }

	        model.addAttribute("patientId", patientId);
	        return "KAppointment/appointment-list";
	    }


	    
	    
	    @GetMapping("/examination-room-form")
	    public String showExaminationRoomForm() {
	        return "KAppointment/examination-room-form";
	    }

	    @GetMapping("/api/appointment/room/patientId")
	    public String getExaminationRoomByPatientIdAndDate(
	            @RequestParam("patientid") int patientId,
	            @RequestParam("date") String date,
	            Model model) {
	        try {
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
	            LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
	            String room = appointmentService.getExaminationRoomByPatientIdAndDate(patientId, localDateTime.toLocalDate());
	            model.addAttribute("room", room);
	        } catch (DateTimeParseException e) {
	            model.addAttribute("room", "Invalid date format: " + e.getMessage());
	        } catch (Exception e) {
	            model.addAttribute("room", "Error fetching data: " + e.getMessage());
	        }
	        return "KAppointment/examination-room-form";
	    }
	    
	    
	    // Method to display the form to search nurses by patient ID
	    @GetMapping("/search-nurses")
	    public String showSearchNursesForm(Model model) {
	        return "KAppointment/search-nurses-form";
	    }

	    // Method to handle form submission and show the list of nurses
	    @GetMapping("/nurses-by-patientid")
	    public String showNursesByPatientId(@RequestParam("patientid") int patientId, Model model) {
	        try {
	            List<Nurse> nurses = appointmentService.getNursesByPatientId(patientId);
	            model.addAttribute("nurses", nurses);
	            model.addAttribute("patientId", patientId);
	        } catch (NoNurseFoundByPatientIdException e) {
	            model.addAttribute("nurses", null);
	            model.addAttribute("patientId", patientId);
	        }
	        return "KAppointment/nurses-list";
	    }

}
