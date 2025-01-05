package com.example.hms.thymecontroller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hms.model.Appointment;
import com.example.hms.model.Patient;
import com.example.hms.model.Physician;
import com.example.hms.model.Room;
import com.example.hms.service.HMSAppointmentService;



@Controller
public class AppointmentThymController {
	@Autowired HMSAppointmentService appService;

	/*@GetMapping("/appointment/home")
	public String showHome() {
		return "appointment/appointment-home.html";
	}*/

	@GetMapping("/appointment/patient/physician")
	public String showFormForPatientByPhysician() {
		return "appointment/form-patientbyphysician.html";
	}

	@GetMapping("/appointment/patient/physician/date/form")
	public String showPatientForm(Model model) {
		return "appointment/form-patientbyphysicianondate.html"; 
	}

	@GetMapping("/appointment/patient/physician/form")
	public String showPatientForm() {
		return "appointment/form-patientidphysicianid.html";
	}

	@GetMapping("/appointment/getPatientByPatientIdAndPhysicianId")
	public String getPatientByPhysicianAndPatientId(@RequestParam("physicianId") int physicianId,
			@RequestParam("patientId") int patientId,
			Model model) {
		Patient patient = appService.getPatientByPatientIdAndPhysicianId(physicianId, patientId);

		model.addAttribute("patient", patient);
		if (patient == null) {
			model.addAttribute("message", "No patient found with the given Physician ID and Patient ID.");
		}
		return "appointment/patientListByIds";
	}

	@GetMapping("/appointment/getPatientsByPhysician")
	public String getListOfPatientCheckedByPhysician(@RequestParam("physicianId") int physicianId, Model model) {
		HashSet<Patient> patients = appService.getPatientCheckedByPhysician(physicianId);
		model.addAttribute("patients", patients);
		return "appointment/patientList"; // This will return the patientList.html template
	}

	@GetMapping("/appointment/getPatientsByPhysicianOnDate")
	public String getPatientsByPhysicianAndDate(
			@RequestParam("physicianId") int physicianId,
			@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
			Model model) {
		HashSet<Patient> patients = appService.getPatientCheckedByPhysicianOnDate(physicianId, date);
		model.addAttribute("patients", patients);
		return "appointment/patientList.html";
	}

	@GetMapping("/appointment/patient/form")
	public String showPatientByAppointmentForm() {
		return "appointment/form-patientbyappointment.html";
	}

	@GetMapping("/appointment/getPatientByAppointment")
	public String getPatientByAppointmentId(@RequestParam("appointmentId") int appointmentId, Model model) {
		Patient patient = appService.getPatientByAppointment(appointmentId);

		model.addAttribute("patient", patient);

		return "appointment/patientLIstByAppointmnet.html";
	}

	@GetMapping("/appointment/physician/appointment/form")
	public String showPhysicianForm() {
		return "appointment/form-physicianbyappointment.html";
	}

	@GetMapping("/appointment/getPhysicianByAppointment")
	public String getPhysicianByAppointmentId(@RequestParam("appointmentId") int appointmentId, Model model) {
		Physician physician = appService.getPhysicianByAppointment(appointmentId);

		model.addAttribute("physician", physician);

		return "appointment/physicianList.html";
	}

	@GetMapping("/appointment/room/form")
	public String showRoomForm() {
		return "appointment/form-roombyphysicianid";
	}

	@GetMapping("/appointment/getRoomByPhysicianIdOnDate")
	public String getRoomByPhysicianIdAndDateTime(@RequestParam("physicianId") int physicianId,
			@RequestParam("date") String date,
			@RequestParam("time") String time,
			Model model) {
		LocalDate localDate = LocalDate.parse(date);
		LocalTime localTime = LocalTime.parse(time);
		LocalDateTime dateTime = LocalDateTime.of(localDate, localTime);
		List<Room> rooms = appService.getRoomByPhysicianIdOnDate(physicianId, dateTime);

		model.addAttribute("rooms", rooms);

		return "appointment/roomList";
	}
	
	@GetMapping("/appointment/update-room/form")
    public String showUpdateRoomForm() {
        return "appointment/form-updateexaminationroom";
    }
	
	 @PostMapping("/appointment/updateExaminationRoom")
	    public String updateExaminationRoom(@RequestParam("appointmentId") int appointmentId,
	                                        @RequestParam("roomNumber") String roomNumber,
	                                        Model model) {
	        // Update the examination room
	        Appointment updatedAppointment = appService.updateExaminationRoom(appointmentId, roomNumber);

	        if (updatedAppointment != null) {
	            model.addAttribute("appointment", updatedAppointment);
	            return "appointment/appointmentList.html";
	        } else {
	            model.addAttribute("message", "Failed to update examination room.");
	            return "appointment/appointmenterror.html";
	        }
	    }
}