package com.example.hms.thymecontroller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hms.model.Physician;
import com.example.hms.model.Procedures;
import com.example.hms.model.Trained_In;
import com.example.hms.serviceImpl.HMSTrainedInServiceImpl;

import lombok.AllArgsConstructor;

@Controller
@Transactional
@AllArgsConstructor
public class LHMSTrainedInThymeController {

	private final HMSTrainedInServiceImpl trainedService;
	
	@GetMapping("/thyme/trained_in/")
	public String viewTrainedInWithCertification(Model model)
	{
		List<Procedures> trainedIn=trainedService.getCertifiedProcedures();
		model.addAttribute("TrainedInWithCertification", trainedIn);
		return "LHome/GetTrainedInWithCertification";
	}
	
	@GetMapping("/thyme/treatment")
	public String viewTrainedInByPhysicianId(Model model, @RequestParam(name="physicianid",required=false) Integer physicianid) {
		 if (physicianid == null) {
		        model.addAttribute("message", "Physician ID is required.");
		        return "LHome/GetTreatmentByPhysicianId";
		    }
		    try {
		    List<Procedures> trainedIn = trainedService.getTreatmentsByPhysician(physicianid);
		    model.addAttribute("procedureByID", trainedIn);
		    }
		    catch(Exception e) {
		        model.addAttribute("message", "No treatments found for Physician ID: " + physicianid);
		    } 
		    return "LHome/GetTreatmentByPhysicianId";
	}

	@GetMapping("/thyme/trained_in/physicians")
	public String viewPhysicianByTreatment(Model model, @RequestParam(name="procedureid", required=false) Integer procedureid) {
	    if (procedureid == null) {
	        model.addAttribute("message", "Procedure ID is required.");
	        return "LHome/GetPhysicianByTreatment";
	    }
	    try {
	    List<Physician> physicians = trainedService.getPhysicianByTreatment(procedureid);
	    model.addAttribute("physicianByTreatment", physicians);
	    }
	    catch(Exception e){
	        model.addAttribute("message", "No physicians found for Procedure ID: " + procedureid);
	    } 
	    return "LHome/GetPhysicianByTreatment";
	}

	
	@GetMapping("/thyme/trained_in/expiredsooncerti")
	public String viewExpiringCertificationsByPhysicianId(Model model,@RequestParam(name="physicianid",required=false) Integer physicianid)
	{
		 if (physicianid == null) 
		 {
		        model.addAttribute("message", "Physician ID is required.");
		        return "LHome/GetExpiringCertificatesByPhysicianId";
		 }
		 try {
		List<Procedures> trainedIn=trainedService.getProceduresWithExpiringCertifications(physicianid);
		model.addAttribute("expiringCertificatesWithPhysicianId", trainedIn);
		 }
		 catch(Exception e)
		 {
			 model.addAttribute("message", "No Expiring certifications for this Physician");
		 }
		return "LHome/GetExpiringCertificatesByPhysicianId";
		
	}
	
//	post
	
	@GetMapping("/register/trainedin")
	public String showForm(Model model) {
	    Trained_In trainedin = new Trained_In();
	    model.addAttribute("trainedin", trainedin);
	    return "LHome/registerTrainedIn";
	}

	@PostMapping("/thyme/postTrainedin")
	public String addTrainedIn(@ModelAttribute("trainedIn") Trained_In trainedin, Model model) {
	    trainedService.saveTrainedIn(trainedin);
	    model.addAttribute("trainedin", trainedin); 
	    return "LHome/viewTrainedIn"; 
	}
	
	
}
