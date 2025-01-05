package com.example.hms.model;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a prescription issued by a physician to a patient for a specific medication.
 * This entity captures the relationship between a physician, a patient, and a medication,
 * as well as the dose prescribed and the associated appointment.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Prescribes")
public class Prescribes {

    /**
     * The composite key for the prescription, composed of the physician, patient, and medication.
     */
    @EmbeddedId
    private PrescribesId id;

    /**
     * The physician who issued the prescription.
     * This relationship is established via the physician's employee ID.
     */
    @ManyToOne
    @MapsId("physician") // Maps the physician from the composite ID
    @JoinColumn(name = "Physician", referencedColumnName = "employeeID")
    private Physician physician;

    /**
     * The patient who is receiving the prescription.
     * This relationship is established via the patient's SSN.
     */
    @ManyToOne
    @MapsId("patient") // Maps the patient from the composite ID
    @JoinColumn(name = "Patient", referencedColumnName = "ssn")
    private Patient patient;

    /**
     * The medication that is being prescribed.
     * This relationship is established via the medication's code.
     */
    @ManyToOne
    @MapsId("medication") // Maps the medication from the composite ID
    @JoinColumn(name = "Medication", referencedColumnName = "code")
    private Medication medication;

    /**
     * The date the prescription was issued.
     * This field is non-updatable and non-insertable to preserve its immutability.
     */
    @Column(name = "Date", nullable = false, insertable = false, updatable = false)
    private LocalDateTime date;

    /**
     * The appointment associated with the prescription.
     * This relationship links the prescription to a specific appointment.
     */
    @ManyToOne
    @JoinColumn(name = "Appointment", referencedColumnName = "appointmentID")
    private Appointment appointment;

    /**
     * The dosage prescribed to the patient for the medication.
     */
    @Column(nullable = false, length = 30, name = "Dose")
    private String dose;

    /**
     * Generates a string representation of the Prescribes object.
     * 
     * @return A string containing the prescription's ID, date, and dose information.
     */
    
}
