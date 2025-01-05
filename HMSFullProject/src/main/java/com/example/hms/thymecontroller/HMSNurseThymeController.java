package com.example.hms.thymecontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hms.model.Nurse;
import com.example.hms.service.HMSNurseService;

@Controller
public class HMSNurseThymeController {
	
	 @Autowired
	    private HMSNurseService nurseservice;
	/*@GetMapping("/r-home")
	public String viewHomePage() {
		
		return "RHome/Home";
		}
	
	@GetMapping("/AllgetEndPoints")
	public String viewEndPoints() {
		
		return "RHome/GetEndPoints";
		}
	
	@GetMapping("/AllpostEndPoints")
	public String postEndPoints() {
		
		return "RHome/PostEndPoints";
		}
	@GetMapping("/AllputEndPoints")
	public String putEndPoints() {
		
		return "RHome/PutEndPoints";
		}*/
	 

	

    @GetMapping("/nurse/newadd")
    public String showAddNurseForm(Model model) {
        model.addAttribute("nurse", new Nurse()); 
        return "Nurse/AddNurse";
    }

    @PostMapping("/thyme/add/nurse")
    public String addNurse(Nurse nurse, Model model) {
        try {
            nurseservice.saveNurse(nurse);
            model.addAttribute("message", "Record Created Successfully");
        } catch (Exception e) {
            model.addAttribute("message", "Error creating nurse: " + e.getMessage());
        }
        return "Nurse/AddNurse";
    }

	
	  
    
    @GetMapping("/thyme/nurse/")
	    public String getAllNurses(Model model) {
	        List<Nurse> nurses = nurseservice.getAll();
	        model.addAttribute("nurses", nurses);
	        return "Nurse/GetAllNurse";
	    }
	   
	 
    
    
    @GetMapping("/thyme/nurse/{empid}")
	    public String getNurseById(@RequestParam(name="empid", required = false) Integer empid, Model model) {
	    	  if (empid == null) {
	              model.addAttribute("message", "Employee ID is required.");
	              return "Nurse/GetNurse";}
	    	  try {
	    	Nurse nurse = nurseservice.getById(empid);
	          model.addAttribute("nurse", nurse);
	            }
	     catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            }
	    	  return "Nurse/GetNurse"; 
	    }	 
	    	 
	    @GetMapping("/thyme/nurse/position")
	    public String getPosById(@RequestParam(name="empid", required = false) Integer empid, Model model) {
	    	  if (empid == null) {
	              model.addAttribute("message", "Employee ID is required.");
	              return "Nurse/GetPosById";
	          }
	    	  
	    	  try {
	    	String position=nurseservice.getPosById(empid);
	     model.addAttribute("position", position);
	     model.addAttribute("empid", empid);
	    	}
	    	  catch (Exception e) {
	              model.addAttribute("message", e.getMessage());
	    	  }
	    	  return "Nurse/GetPosById" ;
	    	
	    }
	   
	    
	    
	    @GetMapping("/thyme/nurse/registered")
	    public String getRegistrationStatus(@RequestParam(name="empid", required = false) Integer empid, Model model) {
	    	 if (empid == null) {
	              model.addAttribute("message", "Employee ID is required.");
	              return "Nurse/GetRegisteredById";
	          }
	    	try {
	    	boolean registered=nurseservice.findRegister(empid);
			 model.addAttribute("registered",registered);
			  model.addAttribute("empid", empid);
	    	}
	    	catch (Exception e) {
	              model.addAttribute("message","NO REGISTRATION STATUS FOUND FOR THIS ID :"+empid);
	    	  }
	    	return "Nurse/GetRegisteredById";
	    	
	    }
	    
	   
	    
	    @GetMapping("/thyme/nurse/ssn")
	    public String showUpdateSsnForm() {
	      return  "Nurse/UpdateSSN";
	    }

	   @PostMapping("/thyme/nurse/ssn/sucess")
	    public String updateSsn(@RequestParam(name="empid") Integer empid,@RequestParam(name="ssn") Integer ssn,Model m) {
	        
	    	try {
	    		Nurse existingNurse = nurseservice.getById(empid);
	    		existingNurse.setSsn(ssn);
	        Nurse updatedNurse = nurseservice.updateSSN(empid,existingNurse);

	        m.addAttribute("nurse", updatedNurse);}
	    	catch (Exception e) {
	              m.addAttribute("message",e.getMessage() );
	    	  }
	        return "Nurse/UpdateSSN";
	    }
	   
	   
	    
	    
	    @GetMapping("/thyme/nurse/registered/update")
	    public String showUpdateRegistartionForm() {
	      return  "Nurse/UpdateRegistrationStatus";
	    }
	    
		@PostMapping(value="/thyme/nurse/registered/sucess")
	    public String updateNurseRegistrationStatus(@RequestParam(name="empid") Integer empid,@RequestParam(name="registered") boolean registered,Model m )
	    {   try {
			Nurse existingNurse = nurseservice.getById(empid);

	        existingNurse.setRegistered(registered);
	        Nurse updatedNurse = nurseservice.updateNurseRegistrationStatus(empid, existingNurse);
	        m.addAttribute("nurse", updatedNurse);}
	        catch (Exception e) {
	              m.addAttribute("message",e.getMessage() );
	    	  }
	        return "Nurse/UpdateRegistrationStatus";
	    }
		
	    
	    
	}
	    
	


