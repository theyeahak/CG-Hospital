package com.example.hms.serviceImpl;
import java.util.List;
import java.util.Optional;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.hms.exception.AlreadyFoundException;
import com.example.hms.exception.NurseNotFoundException;
import com.example.hms.exception.PositionNotFoundException;
import com.example.hms.model.Nurse;
import com.example.hms.repository.NurseRepository;
import com.example.hms.service.HMSNurseService;
 
/**
* Service implementation for managing nurses within the hospital management system.
* This class contains business logic for CRUD operations related to nurses.
*/
@Service
public class HMSNurseServiceImpl implements HMSNurseService{
	private static final Logger logger = LoggerFactory.getLogger(HMSNurseServiceImpl.class);
	@Autowired
	private NurseRepository nurserepo;
	 /**
     * Retrieves a list of all nurses in the system.
     * 
     * @return a List of Nurse entities.
     */
	@Override
	public List<Nurse> getAll(){
		 logger.info("Fetching all nurses from the system.");
	        List<Nurse> nurses = nurserepo.findAll();
	        logger.info("Successfully fetched {} nurses.", nurses.size());
	        return nurses;
		//return  nurserepo.findAll();
		}
	/**
     * Retrieves a nurse by their employee ID.
     * 
     * @param empid the ID of the nurse to retrieve.
     * @return the Nurse entity with the specified ID.
     * @throws NurseNotFoundException if no nurse is found with the given ID.
     */
	@Override
	public Nurse getById(int empid) {
		logger.info("Fetching nurse with ID {}", empid);
		Optional<Nurse> pro=nurserepo.findById(empid);
		if(pro.isEmpty())
		{
			logger.error("No nurse found for ID {}", empid);
			throw new NurseNotFoundException("No nurse found for this id: "+ empid );
			}
		logger.info("Successfully fetched nurse with ID {}", empid);
        return pro.get();
		}
	/**
     * Retrieves the position of a nurse by their employee ID.
     * 
     * @param empid the ID of the nurse to retrieve the position for.
     * @return the position of the nurse.
     * @throws PositionNotFoundException if no position is found for the given ID.
     */
	@Override
	public String getPosById(int empid) {
		logger.info("Fetching position for nurse with ID {}", empid);
		String pos=nurserepo.findPosById(empid);
		if(pos==null) {
			logger.error("No position found for ID {}", empid);
			throw new PositionNotFoundException(" No Position found for this id:"+empid);
			}
		logger.info("Successfully fetched position for nurse with ID {}", empid);
        return pos;
		}
	 /**
     * Checks if a nurse is registered by their employee ID.
     * 
     * @param empid the ID of the nurse to check registration status.
     * @return true if the nurse is registered, false otherwise.
     */
	@Override
	public boolean findRegister(int empid) {
		logger.info("Checking registration status for nurse with ID {}", empid);
        boolean isRegistered = nurserepo.aboutRegister(empid);
        logger.info("Nurse with ID {} registration status: {}", empid, isRegistered);
        return isRegistered;
		//return nurserepo.aboutRegister(empid);
		}
	/**
     * Saves a new nurse to the system.
     * 
     * @param nurse the Nurse entity to be saved.
     * @return the saved Nurse entity.
     * @throws AlreadyFoundException if a nurse with the same ID already exists.
     */
	@Override
	public Nurse saveNurse(Nurse nurse) {
		int id =nurse.getEmployeeID();
		logger.info("Saving nurse with ID {}", id);
		if(nurserepo.existsById(id)) {
			logger.error("Nurse already found with ID {}", id);
			throw new AlreadyFoundException("Nurse already  found By this Id");
			}
		Nurse savedNurse = nurserepo.save(nurse);
        logger.info("Successfully saved nurse with ID {}", id);
        return savedNurse;
		//return nurserepo.save(nurse);
		}
	/**
     * Updates the registration status of a nurse.
     * 
     * @param empid the ID of the nurse to update.
     * @param n the Nurse entity containing the updated registration status.
     * @return the updated Nurse entity.
     */
	@Override
	public Nurse updateNurseRegistrationStatus(int empid, Nurse n) {
		 logger.info("Updating registration status for nurse with ID {}", empid);
		Optional<Nurse> nurseOptional = nurserepo.findById(empid);
		if (nurseOptional.isPresent()) {
			Nurse nurse = nurseOptional.get();
			nurse.setRegistered(n.getRegistered());
			Nurse updatedNurse = nurserepo.save(nurse);
            logger.info("Successfully updated registration status for nurse with ID {}", empid);
            return updatedNurse;
			//return nurserepo.save(nurse);
			}
		logger.error("Nurse with ID {} not found for updating registration status", empid);
        throw new NurseNotFoundException("No nurse found for this id: " + empid);
		//return null;
		}
	/**
     * Updates the Social Security Number (SSN) of a nurse.
     * 
     * @param empid the ID of the nurse to update.
     * @param n the Nurse entity containing the updated SSN.
     * @return the updated Nurse entity.
     */
	@Override
	public Nurse updateSSN(int empid,Nurse n) {
		Optional<Nurse> nurseOptional=nurserepo.findById(empid);
		if(nurseOptional.isPresent()) {
			Nurse nurse=nurseOptional.get();
			nurse.setSsn(n.getSsn());
			Nurse updatedNurse=nurserepo.save(nurse);
			return updatedNurse;
			}
		throw new NurseNotFoundException("No nurse found for this id:"+empid);
	}
}
