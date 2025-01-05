package com.example.hms.thymecontroller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hms.model.Appointment;
import com.example.hms.serviceImpl.HMSAppointmentServiceImpl;

import lombok.AllArgsConstructor;

@Controller
@Transactional
@AllArgsConstructor
public class LHMSAppointmentThymeController {

	private final HMSAppointmentServiceImpl appointService;
	
	@GetMapping("thyme/appointment")
	public String viewAllAppointments(Model model)
	{
		List<Appointment> allAppointments=appointService.getAllAppointments();
		model.addAttribute("allAppointments", allAppointments);
		return "LHome/GetAllAppointments";
	}
	
	@GetMapping("/thyme/startappointment")
	public String viewAppointmentsByStartdate(Model model, @RequestParam(name="startdate",required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startdate)
	{
		try {
		List<Appointment> appointmentsByStartdate=appointService.getAppointmentsByStartDate(startdate);
		model.addAttribute("appointmentsByStartdate", appointmentsByStartdate);
		}
		catch(Exception e)
		{
			model.addAttribute("message", "check the Date format");
		}
		return "LHome/GetAppointmentsByStartdate";
	}
	
	
}
