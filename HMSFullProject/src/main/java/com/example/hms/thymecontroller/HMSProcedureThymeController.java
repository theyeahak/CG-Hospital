package com.example.hms.thymecontroller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hms.model.Procedures;
import com.example.hms.serviceImpl.HMSProceduresServiceImpl;

import lombok.AllArgsConstructor;

@Controller
@Transactional
@AllArgsConstructor
public class HMSProcedureThymeController {

    private final HMSProceduresServiceImpl proService;
    
    @GetMapping("/thyme/procedure/")
    public String viewAllProcedures(Model model) {
        List<Procedures> procedures = proService.getAllProcedures();
        model.addAttribute("procedures", procedures); 
        return "LHome/GetAllProcedures"; 
    }

	
	@GetMapping("/thyme/procedure/cost")
	public String viewProcedureCostById(Model model, @RequestParam(name="id",required=false) Integer id) {
		if(id==null)
		 {
			 model.addAttribute("message", "id is required.");
			 return "LHome/GetProcedureCostById";
		 }try {
	    Procedures procedures = proService.getProceduresById(id);
	    model.addAttribute("procedureCostByID", procedures);}
	   catch(Exception e) {
	        model.addAttribute("message", "Procedure Id not found");
	        return "LHome/GetProcedureCostById";
	    }
	  
	    return "LHome/GetProcedureCostById";
	}
	
	@GetMapping("/thyme/cost")
	public String viewProcedureCostByName(Model model, @RequestParam(name = "name", required = false) String name) {
	    if (name == null || name.isEmpty()) {
	        model.addAttribute("message", "Name is required.");
	        return "LHome/GetProcedureCostByName";
	    }
	    try {
	    Procedures procedure = proService.getProcedureByName(name);
	    model.addAttribute("ProcedureCostByName", procedure);
	    }
	   catch(Exception e) {
	        model.addAttribute("message", "Procedure Name not found");
	        return "LHome/GetProcedureCostByName";
	    }
	    
	    return "LHome/GetProcedureCostByName";
	}
//	post
	@GetMapping("/register/Procedure")
	public String showForm(Model model) {
	    Procedures pro = new Procedures();
	    model.addAttribute("procedure", pro);
	    return "LHome/registerPro";
	}

	@PostMapping("/thyme/postprocedure")
	public String addProcedure(@ModelAttribute("procedure") Procedures procedure, Model model) {
	    proService.addProcedure(procedure);
	    model.addAttribute("procedure", procedure); // Add the procedure to the model
	    return "LHome/viewProcedure"; 
	}
	
//	put cost
	@GetMapping("/thyme/getProcedure")
    public String showCostUpdateprocedure() {
      return  "LHome/updateCost";
    }

   @PostMapping("/thyme/procedure/putcost")
    public String updateCost(@RequestParam(name="code") Integer code,@RequestParam(name="cost") Double cost,Model model) {
    	try {
    		Procedures pro = proService.getProceduresById(code);
    		pro.setCost(cost);
        Procedures updatedPro = proService.updateProcedureCost(code, cost);

        model.addAttribute("procedure", updatedPro);}
    	catch (Exception e) {
              model.addAttribute("message","Pocedure Id not found" );
    	  }
        return "LHome/putCostProcedure";
    }
   
//   put name
	@GetMapping("/thyme/getPro")
    public String showNameUpdateprocedure() {
      return  "LHome/updateName";
    }

   @PostMapping("/thyme/pro/putname")
    public String updateName(@RequestParam(name="code") Integer code,@RequestParam(name="name") String name,Model model) {
    	try {
//    		Procedures pro = proService.getProceduresById(code);
//    		pro.setName(name);
       Procedures procedure = proService.updateProcedureName(code, name);
            model.addAttribute("procedure",procedure);}
    	catch (Exception e) {
              model.addAttribute("message",e.getMessage());
    	  }
        return "LHome/putNameProcedure";
    }
   
}



