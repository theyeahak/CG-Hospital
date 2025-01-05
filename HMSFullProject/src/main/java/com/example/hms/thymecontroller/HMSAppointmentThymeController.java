package com.example.hms.thymecontroller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hms.model.Appointment;
import com.example.hms.model.Nurse;
import com.example.hms.model.Patient;
import com.example.hms.model.Room;
import com.example.hms.service.HMSAppointmentService;

@Controller
public class HMSAppointmentThymeController {
	@Autowired
    private HMSAppointmentService appService;
 
	  @GetMapping("/thyme/appointment/nurse/appointmentid")
	    public String getNurseByAppointmentId(@RequestParam(name="appointmentid", required = false) Integer appointmentid, Model model) {
	    	  if (appointmentid == null) {
	              model.addAttribute("message", "Appointment ID is required.");
	              return "RAppointment/GetNurseByAppointmentId";
	              }
	    	
	    	 try {
	    	 Nurse nurse = appService.getNurseByAppointmentId(appointmentid);
	          model.addAttribute("nurse", nurse);}
	    	  catch (Exception e) {
	              model.addAttribute("message",e.getMessage() );
	     }
	            return "RAppointment/GetNurseByAppointmentId";
	        }
	  @GetMapping("/thyme/appointment/examinationroom/appointmentid")
	    public String getExaminationRoomByAppointmentId(@RequestParam(name="appointmentid", required = false) Integer appointmentid, Model model){
		  if (appointmentid == null) {
              model.addAttribute("message", "Appointment ID is required.");
              return "RAppointment/GetExaminationRoomByAppId";
              }
	  try {
	     String room=appService.getExamintionRoomByappId(appointmentid);
	           model.addAttribute("room",room);
	           model.addAttribute("appointmentid",appointmentid);}
	           catch (Exception e) {
		              model.addAttribute("message",e.getMessage() );
		     } 
	            return "RAppointment/GetExaminationRoomByAppId" ;
	           }
	  
	 
	  @GetMapping("/thyme/appointment/patient/nurseid")
	  public String getPatientsByNurseId(@RequestParam(name="nurseid", required = false) Integer nurseid, Model model){
		  if (nurseid == null) {
              model.addAttribute("message", "Nurse ID is required.");
              return "RAppointment/GetPatientsByNurseId";
          } 
         
		  try {
			  HashSet<Patient> patients = appService.getPateientsByNusreId(nurseid);
		     model.addAttribute("patients", patients);
		  }
		     catch (Exception e) {
	              model.addAttribute("message",e.getMessage() );
	     } 
		  return  "RAppointment/GetPatientsByNurseId";
		  
	  }
	  
	  @GetMapping("/thyme/appointment/patients/nurseidwithdate")
	  public String getPatientsByNurseIdAndDate(@RequestParam(name="nurseid", required = false) Integer nurseid,@RequestParam(name="date", required = false) LocalDate date, Model model){
		  if (nurseid == null ) {
              model.addAttribute("message", "Nurse ID  and Date are required .");
              return "RAppointment/GetPatientsByNurseIdAndDate";
          }
		  try {
			  
			  HashSet<Patient> patients=appService.getPatientsBynurseandDate(nurseid,date);
		     model.addAttribute("patients", patients);}
		     catch (Exception e) {
	              model.addAttribute("message",e.getMessage() );
	     } 
		  return  "RAppointment/GetPatientsByNurseIdAndDate";
		  
	  }
	  
	  @GetMapping("/thyme/appointment/patient/nurseidAndpatientid")
	  public String getPatientByPatientIdAndNurseId(@RequestParam(name="nurseid", required = false) Integer nurseid, @RequestParam(name="patientid", required = false) Integer patientid,   Model model) {
	      if (nurseid == null && patientid == null) {
	          model.addAttribute("message", "Nurse ID and Patient ID are required.");
	          return "RAppointment/GetPatientByPatientIdAndNurseId";
	      }
	      
	      try {
	    	  Patient patient = appService.getPatientByPatientIdAndNurseId(nurseid, patientid);
	      model.addAttribute("patient", patient);}
	      catch (Exception e) {
              model.addAttribute("message",e.getMessage() );
     } 
	       return "RAppointment/GetPatientByPatientIdAndNurseId";
	  }
	  
	
	  @GetMapping("/thyme/appointment/room/nurseidwithdate")
	  public String getAllRoomDetailsWithNurseandDate(@RequestParam(name="nurseid", required = false) Integer nurseid,@RequestParam(name="date", required = false) LocalDateTime date, Model model) {
		  if (nurseid == null) {
              model.addAttribute("message", "Nurse ID and Date are required.");
              return "RAppointment/GetAllRoomDetailsWithNurseandDate";
          }
		 try {
		  List<Room> rooms = appService.getRoomList(nurseid, date);
		  model.addAttribute("rooms",rooms);}
		 catch (Exception e) {
             model.addAttribute("message",e.getMessage() );
    } 
		  return "RAppointment/GetAllRoomDetailsWithNurseandDate";
		  
	  }
	  
	  @GetMapping("/thyme/appointment/newadd")
	    public String showAddAppointmentForm(Model model) {
	        model.addAttribute("appointment", new Appointment()); 
	        return "RAppointment/AddAppointment";
	    }

	    @PostMapping("/thyme/add/appointment")
    public String addAppointment(Appointment appointment, Model model) {
        try {
        	appService.postApp(appointment);
            model.addAttribute("message", "Record Created Successfully");
        } catch (Exception e) {
        	System.err.println("Error creating appointment: " + e.getMessage());
            model.addAttribute("message", "Error creating Appointment: " + e.getMessage());
        }
	        return "RAppointment/viewAppointment";
	    }
	  
	  
	  
	  
	  
	  
	  
}
