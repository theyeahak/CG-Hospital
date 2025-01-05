package com.example.hms.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.hms.exception.NoProceduresFoundException;
import com.example.hms.exception.PhysicianIdNotFoundException;
import com.example.hms.exception.TrainedInWithThisPhysicianIdNotFoundException;
import com.example.hms.model.Physician;
import com.example.hms.model.Procedures;
import com.example.hms.model.Trained_In;
import com.example.hms.repository.ProceduresRepository;
import com.example.hms.repository.Trained_In_Repository;
import com.example.hms.service.HMSTrainedInService;

import lombok.AllArgsConstructor;

/**
 * Implementation of HMSTrainedInService.
 * Provides methods for managing training and certifications related to physicians and procedures.
 */
@Service
@AllArgsConstructor
public class HMSTrainedInServiceImpl implements HMSTrainedInService {

    private static final Logger logger = LoggerFactory.getLogger(HMSTrainedInServiceImpl.class);

    private final Trained_In_Repository trainrepo;
    private final ProceduresRepository proRepo;

    /**
     * Retrieves treatments a physician is trained in.
     * @param physicianId The ID of the physician.
     * @return A list of treatments.
     * @throws TrainedInWithThisPhysicianIdNotFoundException If no treatments are found.
     */
    public List<Procedures> getTreatmentsByPhysician(Integer physicianId) {
        logger.info("Fetching treatments for physician ID: {}", physicianId);

        List<Trained_In> trainedInList = trainrepo.findByPhysician(physicianId);
        List<Procedures> treatments = new ArrayList<>();
        for (Trained_In trainedIn : trainedInList) {
            treatments.add(trainedIn.getTreatment());
        }

        if (trainedInList.isEmpty()) {
            logger.error("No TrainedIn records found for physician ID: {}", physicianId);
            throw new TrainedInWithThisPhysicianIdNotFoundException(
                "No TrainedIn found for this PhysicianID: " + physicianId
            );
        }

        return treatments;
    }

    /**
     * Retrieves physicians trained in a specific treatment.
     * @param treatmentCode The code of the treatment.
     * @return A list of physicians.
     * @throws PhysicianIdNotFoundException If no physicians are found.
     */
    public List<Physician> getPhysicianByTreatment(Integer treatmentCode) {
        logger.info("Fetching physicians trained in treatment code: {}", treatmentCode);

        List<Trained_In> trainedInList = trainrepo.findByTreatmentCode(treatmentCode);
        List<Physician> physicians = new ArrayList<>();
        for (Trained_In trainedIn : trainedInList) {
            physicians.add(trainedIn.getPhysician());
        }

        if (physicians.isEmpty()) {
            logger.error("No physicians found for treatment code: {}", treatmentCode);
            throw new PhysicianIdNotFoundException("No Physicians found.");
        }

        return physicians;
    }

    /**
     * Retrieves all distinct procedures with certifications.
     * @return A list of procedures.
     * @throws NoProceduresFoundException If no procedures are found.
     */
    public List<Procedures> getCertifiedProcedures() {
        logger.info("Fetching certified procedures");

        List<Trained_In> trainedInList = trainrepo.findDistinctByTreatmentIsNotNull();
        Set<Procedures> uniqueProcedures = new HashSet<>();

        for (Trained_In trainedIn : trainedInList) {
            uniqueProcedures.add(trainedIn.getTreatment());
        }

        List<Procedures> proceduresList = new ArrayList<>(uniqueProcedures);

        if (proceduresList.isEmpty()) {
            logger.error("No certified procedures found");
            throw new NoProceduresFoundException("No procedures found with expiring certificates.");
        }

        return proceduresList;
    }

    /**
     * Saves a trained-in record.
     * @param trainedIn The Trained_In object to be saved.
     * @return The saved Trained_In object.
     */
    public Trained_In saveTrainedIn(Trained_In trainedIn) {
        logger.info("Saving trained-in record for physician ID: {}", trainedIn.getPhysician().getEmployeeID());
        return trainrepo.save(trainedIn);
    }

    /**
     * Retrieves all trained-in records.
     * @return A list of trained-in records.
     */
    public List<Trained_In> findAll() {
        logger.info("Fetching all trained-in records");
        return trainrepo.findAll();
    }

    /**
     * Retrieves procedures with expiring certifications for a physician.
     * @param physicianId The ID of the physician.
     * @return A list of procedures.
     * @throws NoProceduresFoundException If no procedures are found.
     */
    public List<Procedures> getProceduresWithExpiringCertifications(Integer physicianId) {
        logger.info("Fetching procedures with expiring certifications for physician ID: {}", physicianId);

        List<Trained_In> trainedList = trainrepo.findProceduresWithExpiringCertifications(physicianId);
        List<Integer> codes = new ArrayList<>();
        for (Trained_In t : trainedList) {
            codes.add(t.getTreatment().getCode());
        }

        List<Procedures> proList = new ArrayList<>();
        for (Integer i : codes) {
            proList.add(proRepo.findById(i).orElseThrow(() -> 
                new NoProceduresFoundException("Procedure ID " + i + " not found.")
            ));
        }

        if (proList.isEmpty()) {
            logger.error("No procedures with expiring certifications found for physician ID: {}", physicianId);
            throw new NoProceduresFoundException(
                "No procedures found with expiring certificates for this physician ID: " + physicianId
            );
        }

        return proList;
    }

    /**
     * Updates the certification expiry for a given physician and procedure.
     * @param physicianId The physician ID.
     * @param procedureId The procedure ID.
     * @param newCertificationExpiry The new expiry date.
     * @return true if the update was successful, false otherwise.
     */
    public Boolean updateCertificationExpiry(int physicianId, int procedureId, LocalDateTime newCertificationExpiry) {
        logger.info(
            "Updating certification expiry for physician ID: {} and procedure ID: {} to {}",
            physicianId, procedureId, newCertificationExpiry
        );

        Trained_In t = trainrepo.findByPhysicianAndTreatment(physicianId, procedureId);

        if (t == null) {
            logger.error("No Trained_In record found for physician ID: {} and procedure ID: {}", physicianId, procedureId);
            return false;
        }

        t.setCertificationExpires(newCertificationExpiry);
        trainrepo.save(t);

        logger.info("Certification expiry updated successfully for physician ID: {} and procedure ID: {}", physicianId, procedureId);
        return true;
    }
}
