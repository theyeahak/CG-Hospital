package com.example.hms.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.hms.exception.NoProceduresFoundException;
import com.example.hms.exception.PhysicianIdNotFoundException;
import com.example.hms.model.Physician;
import com.example.hms.model.Procedures;
import com.example.hms.model.Trained_In;
import com.example.hms.service.HMSTrainedInService;

import lombok.AllArgsConstructor;

/**
 * Controller for managing Trained_In-related APIs.
 * Provides endpoints for managing training records and certifications of physicians.
 */
@RestController
@Transactional
@AllArgsConstructor
public class HMSTrainedInController {

    private static final Logger logger = LoggerFactory.getLogger(HMSTrainedInController.class);

    private final HMSTrainedInService trainedInService;

    /**
     * Retrieves treatments for a specific physician by their ID.
     * @param physicianID The ID of the physician.
     * @return ResponseEntity containing the list of procedures and HTTP status OK.
     */
    @GetMapping("/api/trained_in/treatment/{physicianId}")
    public ResponseEntity<List<Procedures>> getTreatmentsByPhysician(@PathVariable("physicianId") Integer physicianID) {
        logger.info("Fetching treatments for physician ID: {}", physicianID);
        List<Procedures> treatments = trainedInService.getTreatmentsByPhysician(physicianID);
        return ResponseEntity.ok(treatments);
    }

    /**
     * Retrieves physicians trained in a specific treatment by treatment code.
     * @param treatmentCode The code of the treatment.
     * @return ResponseEntity containing the list of physicians and HTTP status OK.
     */
    @GetMapping("/api/trained_in/physicians/{procedureid}")
    public ResponseEntity<List<Physician>> getPhysiciansByTreatment(@PathVariable("procedureid") Integer treatmentCode) {
        logger.info("Fetching physicians trained in treatment with code: {}", treatmentCode);
        List<Physician> physicians = trainedInService.getPhysicianByTreatment(treatmentCode);
        return new ResponseEntity<>(physicians, HttpStatus.OK);
    }

    /**
     * Retrieves all certified procedures.
     * @return ResponseEntity containing the list of certified procedures and HTTP status OK.
     */
    @GetMapping("/api/trained_in/")
    public ResponseEntity<List<Procedures>> getCertifiedProcedures() {
        logger.info("Fetching all certified procedures.");
        List<Procedures> procedures = trainedInService.getCertifiedProcedures();
        return new ResponseEntity<>(procedures, HttpStatus.OK);
    }

    /**
     * Retrieves procedures with expiring certifications for a specific physician.
     * @param physicianId The ID of the physician.
     * @return ResponseEntity containing the list of procedures and HTTP status OK.
     */
    @GetMapping("/api/trained_in/expiredsooncerti/{physicianid}")
    public ResponseEntity<List<Procedures>> getExpiringCertificates(@PathVariable("physicianid") Integer physicianId) {
        logger.info("Fetching procedures with expiring certifications for physician ID: {}", physicianId);
        List<Procedures> li = trainedInService.getProceduresWithExpiringCertifications(physicianId);
        return new ResponseEntity<>(li, HttpStatus.OK);
    }

    /**
     * Creates a new training record.
     * @param trainedIn The Trained_In object to be created.
     * @return ResponseEntity with a success message and HTTP status CREATED.
     */
    /*@PostMapping("/api/trained_in")
    public ResponseEntity<String> createTrainedIn(@RequestBody Trained_In trainedIn) {
        logger.info("Creating new training record: {}", trainedIn);
        Trained_In savedTrainedIn = trainedInService.saveTrainedIn(trainedIn);
        return new ResponseEntity<>("Record Created Successfully", HttpStatus.CREATED);
    }*/
    @PostMapping("/api/trained_in")
    public ResponseEntity<String> createTrainedIn(@RequestBody Trained_In trainedIn) {
        logger.info("Creating new training record: {}", trainedIn);
        
        // Check if physician and treatment exist
        if (trainedIn.getPhysician() == null || trainedIn.getPhysician().getEmployeeID() == null) {
            throw new PhysicianIdNotFoundException("Physician ID is not found.");
        }
        
        if (trainedIn.getTreatment() == null || trainedIn.getTreatment().getCode() == null) {
            throw new NoProceduresFoundException("Treatment code is not found.");
        }

        Trained_In savedTrainedIn = trainedInService.saveTrainedIn(trainedIn);
        return new ResponseEntity<>("Record Created Successfully", HttpStatus.CREATED);
    }

    /**
     * Retrieves all training records.
     * @return ResponseEntity containing the list of all training records and HTTP status OK.
     */
    @GetMapping("/api/trained_in/getAll")
    public ResponseEntity<List<Trained_In>> getAll() {
        logger.info("Fetching all training records.");
        List<Trained_In> tall = trainedInService.findAll();
        return new ResponseEntity<>(tall, HttpStatus.OK);
    }

    /**
     * Updates the certification expiry date for a specific physician and procedure.
     * @param physicianid The ID of the physician.
     * @param procedureid The ID of the procedure.
     * @param reqBody Request body containing the new certification expiry date.
     * @return ResponseEntity with a boolean indicating success or failure and HTTP status OK.
     */
    @PutMapping("/api/trained_in/certificationexpiry/{physicianid}/{procedureid}")
    public ResponseEntity<Boolean> updateCertificationExpiry(
            @PathVariable("physicianid") int physicianid,
            @PathVariable("procedureid") int procedureid,
            @RequestBody Map<String, LocalDateTime> reqBody) {

        LocalDateTime newCertificationExpiry = reqBody.get("newCertificationExpiry");
        logger.info("Updating certification expiry for physician ID: {}, procedure ID: {}. New expiry: {}",
                physicianid, procedureid, newCertificationExpiry);
        Boolean res = trainedInService.updateCertificationExpiry(physicianid, procedureid, newCertificationExpiry);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
