package com.example.hms;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import com.example.hms.model.Procedures;
import com.example.hms.exception.AlreadyFoundException;
import com.example.hms.exception.ProcedureIdNotFoundException;
import com.example.hms.repository.ProceduresRepository;
import com.example.hms.serviceImpl.HMSProceduresServiceImpl;

@Transactional
@SpringBootTest
class HMSProceduresServiceImplTest {

	@Autowired
	HMSProceduresServiceImpl hmsProceduresService;

	@Autowired
	ProceduresRepository prorepo;

	private Procedures testProcedure;

	@BeforeEach
	void setUp() {
		// initialize test data here
		testProcedure = new Procedures();
		testProcedure.setCode(101);
		testProcedure.setName("Blood Test");
		testProcedure.setCost(200.0);

		// save the initial test data to the repository
		prorepo.save(testProcedure);
	}

	@Test
	void testGetAllProcedures() {
		List<Procedures> procedures = hmsProceduresService.getAllProcedures();
		assertNotNull(procedures);
		assertFalse(procedures.isEmpty());
	}

	@Test
	void testAddProcedure() throws AlreadyFoundException {
		Procedures newProcedure = new Procedures();
		newProcedure.setCode(102);
		newProcedure.setName("X-ray");
		newProcedure.setCost(500.0);
		// save the procedure to the repository and check it
		Procedures addedProcedure = hmsProceduresService.addProcedure(newProcedure);

		assertNotNull(addedProcedure);
		assertEquals("X-ray", addedProcedure.getName());
	}

	@Test
	void testAddProcedure_AlreadyFoundException() {
		// trying to add a procedure that already exists
		testProcedure.setCode(101);
		assertThrows(AlreadyFoundException.class, () -> hmsProceduresService.addProcedure(testProcedure));
	}

	@Test
	void testUpdateProcedureCost() throws ProcedureIdNotFoundException {
		// update the cost of the existing procedure
		testProcedure.setCost(300.0);
		prorepo.save(testProcedure); // Save the updated procedure

		Procedures updatedProcedure = hmsProceduresService.updateProcedureCost(101, 350.0);
		assertNotNull(updatedProcedure);
		assertEquals(350.0, updatedProcedure.getCost());
	}

	@Test
	void testUpdateProcedureCost_ProcedureIdNotFoundException() {
		// trying to update a procedure that doesn't exist
		assertThrows(ProcedureIdNotFoundException.class, () -> hmsProceduresService.updateProcedureCost(999, 350.0));
	}

	@Test
	void testUpdateProcedureName() {
		// i'm updating the name of an existing procedure
		testProcedure.setName("Blood Test Updated");
		prorepo.save(testProcedure);

		Procedures updatedProcedure = hmsProceduresService.updateProcedureName(101, "Blood Test Updated Again");
		assertNotNull(updatedProcedure);
		assertEquals("Blood Test Updated Again", updatedProcedure.getName());
	}
}
