package com.example.hms.serviceImpl;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.example.hms.exception.AffiliatedWithExistsException;
import com.example.hms.exception.NoAffiliationsFoundException;
import com.example.hms.exception.PhysiciansNotFoundException;
import com.example.hms.model.AffiliatedWithId;
import com.example.hms.model.Affiliated_With;
import com.example.hms.model.Department;
import com.example.hms.model.Physician;
import com.example.hms.repository.Affiliated_With_Repository;
import com.example.hms.repository.DepartmentRepository;
import com.example.hms.repository.PhysicianRepository;
import com.example.hms.service.HMSAffiliatedWithService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
 
/**
* Service implementation for managing affiliations between physicians and departments.
* This class contains business logic for creating affiliations, retrieving physicians 
* by department, and managing department affiliations for physicians.
*/
@Service
@Getter
@Setter
@AllArgsConstructor
public class HMSAffiliatedWithServiceImpl implements HMSAffiliatedWithService{
	private static final Logger logger = LoggerFactory.getLogger(HMSAffiliatedWithServiceImpl.class);
	private Affiliated_With_Repository affRepo;
	private PhysicianRepository phyRepo;
	private DepartmentRepository depRepo;
	/**
     * Creates a new affiliation with a physician and a department.
     * 
     * @param affObj the Affiliated_With entity to be created.
     * @return the created Affiliated_With entity.
     * @throws AffiliatedWithExistsException if an affiliation with the same ID already exists.
     */
	@Override
	public Affiliated_With createAffiliatedWith(Affiliated_With affObj) {
		logger.info("Attempting to create an affiliation with ID: {}", affObj.getId());
		AffiliatedWithId id=affObj.getId();
		if(affRepo.existsById(id))
		{
			logger.error("Affiliation with ID {} already exists", id);
			throw new AffiliatedWithExistsException("Affiliated With Already exists");
		}
		Affiliated_With savedAffiliation = affRepo.save(affObj);
        logger.info("Affiliation created successfully with ID: {}", savedAffiliation.getId());
        return savedAffiliation;
	}
	/**
     * Retrieves a list of physicians affiliated with a specific department.
     * 
     * @param deptid the ID of the department to search for affiliated physicians.
     * @return a List of Physician entities affiliated with the specified department.
     * @throws PhysiciansNotFoundException if no physicians are found for the given department ID.
     */
	@Override
	//search a list of physicians affiliated with dept id
	public List<Physician> getPhysiciansByDeptId(Integer deptid) {
		List<Integer> PhysicianIds=affRepo.findByDepartment(deptid);
		List<Physician> phyList=new ArrayList<>();
		for(Integer i:PhysicianIds) {
			phyList.add(phyRepo.findById(i).get());
		}
		if(phyList.isEmpty())
		{
			throw new PhysiciansNotFoundException("No Physicians Found");
		}
		return phyList;
	}
	/**
     * Retrieves a list of departments associated with a specific physician.
     * 
     * @param phyId the ID of the physician to search for affiliated departments.
     * @return a List of Department entities associated with the specified physician.
     */
	@Override
	public List<Department> getDepartmentsByPhyId(Integer phyId) {
		List<Affiliated_With> affList=affRepo.findByPhysician(phyId);
        List<Integer>  DepartmentIds= new ArrayList<>();
        for(Affiliated_With aff:affList) {
        	DepartmentIds.add(aff.getId().getDepartment());
        }
        List<Department> depList=new LinkedList<>();
        for(Integer i:DepartmentIds) {
        	depList.add(depRepo.findById(i).get());
        }
        return depList;
    }
	 /**
     * Counts the number of physicians affiliated with a specific department.
     * 
     * @param deptId the ID of the department to count affiliated physicians.
     * @return the count of physicians affiliated with the specified department.
     */
	@Override
	public Integer findPhyCountByDeptId(Integer deptId) {
		logger.info("Counting physicians for department ID: {}", deptId);
        Integer count = affRepo.findPhyCountByDeptId(deptId);
        logger.info("Found {} physicians for department ID: {}", count, deptId);
        return count;
	}
	/**
     * Checks if a physician has a primary affiliation.
     * 
     * @param physicianId the ID of the physician to check for primary affiliation.
     * @return true if the physician has a primary affiliation, false otherwise.
     * @throws NoAffiliationsFoundException if no affiliations are found for the given physician ID.
     */
	@Override
	public boolean findPrimaryAffByPhyId(Integer physicianId) 
	{
		List<Affiliated_With> affList=affRepo.findByPhysician(physicianId);
		List<Boolean> primaryAffList=new ArrayList<>();
		if(affList.isEmpty()||affList==null)
		{
			throw new NoAffiliationsFoundException("No affiliations found for physician ID: " + physicianId);
		}
		for(Affiliated_With aff:affList) 
		{
			primaryAffList.add(aff.getPrimaryAffiliation());
		}
		if(primaryAffList.size()==1) 
		{
			return primaryAffList.get(0);	
		}
		else {
			return false;
		}
	}
	 /**
     * Retrieves all affiliations in the system.
     * 
     * @return a List of all Affiliated_With entities.
     */
	@Override
	public List<Affiliated_With> getall() {
		logger.info("Fetching all affiliations");
        List<Affiliated_With> affiliations = affRepo.findAll();
        logger.info("Successfully fetched {} affiliations", affiliations.size());
        return affiliations;
	}
}

