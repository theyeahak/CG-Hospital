package com.example.hms.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.hms.exception.ProcedureNameNotFoundException;
import com.example.hms.exception.AlreadyFoundException;
import com.example.hms.exception.ProcedureIdNotFoundException;

import com.example.hms.model.Procedures;
import com.example.hms.repository.ProceduresRepository;
import com.example.hms.service.HMSProceduresService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
//@NoArgsConstructor
public class HMSProceduresServiceImpl implements HMSProceduresService {

	private ProceduresRepository prorepo;
	private static final Logger logger = LoggerFactory.getLogger(HMSProceduresServiceImpl.class);

	// ranjjan
	/**
	 * gets the all possible procedures from the db returns list of procedures
	 */
	public List<Procedures> getAllProcedures() {
		logger.info("Fetching all procedures from the repository.");
		List<Procedures> procedures = prorepo.findAll();
		logger.info("Found {} procedures.", procedures.size());
		return procedures;
	}

	// ranjjan
	/**
	 * this adds a new procedure and its details returns the added procedure here
	 */
	public Procedures addProcedure(Procedures procedure)throws AlreadyFoundException {
		logger.info("adding new procedure: {}", procedure.getName());
		if (prorepo.existsById(procedure.getCode())) {
	        logger.error("Procedure with ID {} already exists.", procedure.getCode());
	        throw new AlreadyFoundException("Procedure already found with this ID: " + procedure.getCode());
	    }
		Procedures savedProcedure = prorepo.save(procedure);
		logger.info("procedure added successfully: {}", savedProcedure.getName());
		return savedProcedure;
	}

	// ranjjan
	/**
	 * updates the cost of an already present procedure here returns the updated
	 * procedure
	 */
	public Procedures updateProcedureCost(Integer id, Double newCost)throws ProcedureIdNotFoundException {
		logger.info("Updating cost for procedure with ID: {}", id);
		Optional<Procedures> procedure = prorepo.findById(id);

		if (procedure.isEmpty()) {
			logger.error("Procedure with ID: {} not found.", id);
			throw new ProcedureIdNotFoundException("Procedure not found with id: " + id);
		}

		procedure.get().setCost(newCost);
		Procedures updatedProcedure = prorepo.save(procedure.get());
		logger.info("Procedure cost updated for ID: {}", id);
		return updatedProcedure;
	}

	// ranjjan
	/**
	 * updates the already present procedure's name returns updated procedure
	 * 
	 */
	public Procedures updateProcedureName(Integer id, String newName) {
		logger.info("upppdating name for procedure with ID: {}", id);
		Procedures procedure = prorepo.findById(id).get();
		procedure.setName(newName);
		logger.info("procedure name updated for ID: {}", id);
		return prorepo.save(procedure);
	}
	
	 /**
     * Retrieves a procedure by its ID.
     * 
     * @param procedureId The ID of the procedure.
     * @return The procedure with the specified ID.
     * @throws ProcedureIdNotFoundException If no procedure with the specified ID is found.
     */
	
	public Procedures getProceduresById(int procedureId)
	{
		return prorepo.findById(procedureId).get();
	}
	 /**
     * Retrieves a procedure by its name.
     * 
     * @param ProcedureName The name of the procedure.
     * @return The procedure with the specified name.
     * @throws ProcedureNameNotFoundException If no procedure with the specified name is found.
     */
	public Procedures getProcedureByName(String ProcedureName)
	{
		Procedures pro=prorepo.findByName(ProcedureName);
		if(pro==null)
		{
			throw new ProcedureNameNotFoundException("No procedures found for this name: "+ ProcedureName );
		}
		return prorepo.findByName(ProcedureName);
		
	}
	
	
	
	
	

}
