package com.example.hms.thymecontroller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hms.model.AffiliatedWithId;
import com.example.hms.model.Affiliated_With;
import com.example.hms.model.Department;
import com.example.hms.model.Physician;
import com.example.hms.repository.Affiliated_With_Repository;
import com.example.hms.repository.DepartmentRepository;
import com.example.hms.repository.PhysicianRepository;
import com.example.hms.serviceImpl.HMSAffiliatedWithServiceImpl;
import com.example.hms.serviceImpl.HMSDepartmentServiceImpl;

import lombok.AllArgsConstructor;

@Controller
@Transactional
@AllArgsConstructor
public class HMSAffiliatedWithThymeController {
	
	private final HMSAffiliatedWithServiceImpl affService;
	private final HMSDepartmentServiceImpl deptService;
	private final PhysicianRepository phyRepo;
	private final DepartmentRepository deptRepo;
	private final Affiliated_With_Repository affRepo;
	
	 @GetMapping("/thyme/affiliated_with/physicians")
	    public String PhysAffWithDeptId(Model model, @RequestParam(name="deptid",required=false) Integer deptid) {
		 if(deptid==null)
		 {
			 model.addAttribute("message", "Department ID is required.");
			 return "AffiliatedWith/PhyAffDeptId";
		 }
		 try {
			 List<Physician> physicians = affService.getPhysiciansByDeptId(deptid);
			 model.addAttribute("physicians", physicians); 
		 }
		 catch(Exception e) {
			 model.addAttribute("message", e.getMessage());
		 } 
	        return "AffiliatedWith/PhyAffDeptId";
	    }
	 
	 @GetMapping("/thyme/affiliated_with/department")
	    public String DeptsAffwithPhyId(Model model, @RequestParam(name="physicianid",required=false) Integer phyId) {
		 if(phyId==null)
		 {
			 model.addAttribute("message", "Physician ID is required.");
			 return "AffiliatedWith/DeptsAffPhyId";
		 }
		 try {
			 List<Department> departments = affService.getDepartmentsByPhyId(phyId);
			 model.addAttribute("departments", departments); 
		 }
		 catch(Exception e) {
			 model.addAttribute("message", e.getMessage());
		 }
	       
	        return "AffiliatedWith/DeptsAffPhyId";
	    }
	
	
	
	@GetMapping("/thyme/affiliated_with/countphysician")
    public String CountPhyAffDept(Model model, @RequestParam(name="deptid",required=false) Integer deptId) {
	 if(deptId==null)
	 {
		 model.addAttribute("message", "Department ID is required.");
		 return "AffiliatedWith/viewPhyCountAffDept";
	 }
	 try {
		 	Integer i=affService.findPhyCountByDeptId(deptId);
		 	model.addAttribute("output", i);
	 }
	 catch(Exception e) {
		 model.addAttribute("message", e.getMessage());
	 }
	 	 
		return "AffiliatedWith/viewPhyCountAffDept";
    }

	
	
	@GetMapping("/thyme/affiliated_with/primary")
    public String viewPrimaryAffByPhyId(Model model, @RequestParam(name="physicianid",required=false) Integer PhyId) {
	 if(PhyId==null)
	 {
		 model.addAttribute("message", "Physician ID is required.");
		 return "AffiliatedWith/viewPrimaryAffByPhyId";
	 }
	 try {
		 	boolean value=affService.findPrimaryAffByPhyId(PhyId);
			model.addAttribute("output", value);
	 }
	 catch(Exception e) {
		 model.addAttribute("message", e.getMessage());
	 }
	 
	 
		return "AffiliatedWith/viewPrimaryAffByPhyId";
    }
	
	

	
	//add new AffilaitedWith
    @GetMapping("/register/affiliatedWith")
    public String showForm(Model model) {
    	Affiliated_With affWith = new Affiliated_With();
	    model.addAttribute("affWith", affWith);
        return "AffiliatedWith/registerAff";
    }
    
    @PostMapping("/thyme/affiliated_with/post")
    public String addAffiliatedWith1(@RequestParam("physician") Integer physicianID,
                                     @RequestParam("department") Integer departmentID,
                                     @RequestParam("primaryAffiliation") Boolean primaryAffiliation,
                                     Model model) {
        Affiliated_With affWith = new Affiliated_With();
        AffiliatedWithId affId = new AffiliatedWithId();
        affId.setDepartment(departmentID);
        affId.setPhysician(physicianID);
        affWith.setId(affId);
        affWith.setPrimaryAffiliation(primaryAffiliation);

        // Ensure that the department and physician are not null
        Department department = deptRepo.findById(departmentID).orElse(null);
        Physician physician = phyRepo.findById(physicianID).orElse(null);
        if (department == null || physician == null) {
            // Handle the error, e.g., by returning an error message or redirecting
            model.addAttribute("errorMessage", "Invalid department or physician ID.");
            return "AffiliatedWith/AddAffiliatedWith";
        }

        affWith.setDepartment(department);
        affWith.setPhysician(physician);

        affRepo.save(affWith);

        return "AffiliatedWith/successPage";
    }

    
   
	 



	
	
}
