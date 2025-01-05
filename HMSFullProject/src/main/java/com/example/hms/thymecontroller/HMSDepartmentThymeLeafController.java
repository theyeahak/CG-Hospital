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

import com.example.hms.exception.DepartmentIdNotFoundException;
import com.example.hms.exception.DepartmentsNotFoundException;
import com.example.hms.exception.NoTrainedInsFoundException;
import com.example.hms.exception.PhysiciansNotFoundException;
import com.example.hms.model.Department;
import com.example.hms.model.Physician;
import com.example.hms.model.Trained_In;
import com.example.hms.serviceImpl.HMSDepartmentServiceImpl;

import lombok.AllArgsConstructor;

@Controller
@Transactional
@AllArgsConstructor
public class HMSDepartmentThymeLeafController {

    private final HMSDepartmentServiceImpl deptService;
    
    @GetMapping("/thyme/departments/")
    public String viewAllDepartments(Model model) {
        List<Department> departments = deptService.getAllDepartments();
        model.addAttribute("departments", departments); 
        return "Department/GetAllDepartments";
    }
    
    
    @GetMapping("/thyme/department")
    public String viewDepartmentById(Model model, @RequestParam(name="deptid",required=false) Integer deptid) {
	 if(deptid==null)
	 {
		 model.addAttribute("message", "Department ID is required.");
		 return "Department/viewDepartmentById";
	 }
	 
       
       try {
    	   Department department=deptService.getDepartmentById(deptid);
           model.addAttribute("department", department);
       }
       catch(DepartmentIdNotFoundException e) {
    	   model.addAttribute("message", e.getMessage());
       }
        return "Department/viewDepartmentById";
    }
    
    
    @GetMapping("/thyme/department/head")
    public String viewHeadByDeptId(Model model, @RequestParam(name="deptid",required=false) Integer deptid) {
	 if(deptid==null)
	 {
		 model.addAttribute("message", "Department ID is required.");
		 return "Department/viewHeadByDeptId";
	 }
	 try {
		 Physician phy=deptService.getHeadByDeptId(deptid);
		 model.addAttribute("physician", phy);
	 }
	 catch(Exception e)
	 {
		 model.addAttribute("message", e.getMessage());
	 }
	 
    	return "Department/viewHeadByDeptId";
    }
    
    @GetMapping("/thyme/department/headcertification")
    public String viewHeadCertificationsByDeptId(Model model, @RequestParam(name="deptid",required=false) Integer deptid) {
	 if(deptid==null)
	 {
		 model.addAttribute("message", "Department ID is required.");
		 return "Department/viewCertifications";
	 }
	 try {
		 List<Trained_In> trainedList=deptService.findCertificationsByDeptid(deptid);
		 model.addAttribute("trainedIn", trainedList);
	 }
	 catch(NoTrainedInsFoundException e)
	 {
		 model.addAttribute("message", e.getMessage());
	 }
	 catch(Exception e)
	 {
		 model.addAttribute("message", e.getMessage());
	 }
	 
    	return "Department/viewCertifications";
    }
    
    
    @GetMapping("/thyme/departmentsByHead")
    public String viewDeptsByHead(Model model, @RequestParam(name="head",required=false) Integer head) {
	 if(head==null)
	 {
		 model.addAttribute("message", "Head ID is required.");
		 return "Department/deptsByHead";
	 }
	 try {
		 List<Department> departments=deptService.getDepartmentsByHead(head);
	        model.addAttribute("departments", departments);
	 }
	 catch(DepartmentsNotFoundException e)
	 {
		 model.addAttribute("message", e.getMessage());
	 }
	 
    	return "Department/deptsByHead";
    }
    
   
    @GetMapping("/thyme/department/check")
    public String checkDeptExistsByPhyId(Model model, @RequestParam(name="physicianid",required=false) Integer phyId) {
	 if(phyId==null)
	 {
		 model.addAttribute("message", "Physician ID is required.");
		 return "Department/DeptExistsByPhyId";
	 }
	 
		 	Boolean value=deptService.existsByPhysicianId(phyId);
		 	model.addAttribute("output", value);
		 return "Department/DeptExistsByPhyId";
    }
    
    //add new Department
    @GetMapping("/register/department")
    public String showForm(Model model) {
        Department dept = new Department();
        model.addAttribute("department", dept);
        return "Department/registerDept";
    }

    @PostMapping("/thyme/department/post")
    public String submitForm(@ModelAttribute("department") Department department, Model model) {
        deptService.createDepartment(department);
        model.addAttribute("department", department); // Add the department to the model
        return "Department/viewDepartment"; 
    }
    
    //update Department Head Id
    @GetMapping("/thyme/update/head")
    public String showUpdateHeadForm()
    {
    	return "Department/UpdateHead";
    }
    
    @PostMapping("/thyme/department/update/headid")
    public String updateHead(@RequestParam(name="departmentID") Integer departmentID,@RequestParam(name="head") Physician head,Model model)
    {
    	try {
    		Department exitsing =deptService.getDepartmentById(departmentID);
    		exitsing.setHead(head);
    		Department updatedDept=deptService.updateHeadByDeptId(departmentID, head);
    		model.addAttribute("department", updatedDept);
    	}
    	catch(Exception e)
    	{
    		model.addAttribute("message", e.getMessage());
    	}
    	return "Department/UpdateHeadSuccess";
    }
    
    // update departmentName in the department
    @GetMapping("/thyme/update/deptname")
    public String showUpdateDeptNameForm()
    {
    	return "Department/UpdateDeptName";
    }
    
    @PostMapping("/thyme/department/update/deptname")
    public String updateDeptName(@RequestParam(name="departmentID") Integer departmentID,@RequestParam(name="name") String name,Model model)
    {
    	try {
    		Department exitsing =deptService.getDepartmentById(departmentID);
    		exitsing.setName(name);
    		Department updatedDept=deptService.updateDepartmentName(departmentID, name);
    		model.addAttribute("department", updatedDept);
    	}
    	catch(Exception e)
    	{
    		model.addAttribute("message", e.getMessage());
    	}
    	return "Department/UpdateDeptNameSuccess";
    }
    
   
  
    
    
 
    
}
