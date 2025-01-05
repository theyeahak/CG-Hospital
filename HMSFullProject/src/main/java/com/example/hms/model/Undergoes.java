package com.example.hms.model;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the relationship between a patient, their treatment procedures, 
 * the medical staff involved, and the hospital stay during which the treatment occurred.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Undergoes")
public class Undergoes {

    /**
     * Composite primary key consisting of patient, procedure, stay, physician, and assisting nurse.
     */
    @EmbeddedId
    private UndergoesId id;

    /**
     * The patient who undergoes the procedure.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Patient", referencedColumnName = "SSN", insertable = false, updatable = false)
    private Patient patient;

    /**
     * The procedure (medical treatment) that the patient undergoes.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Procedures", referencedColumnName = "Code", insertable = false, updatable = false)
    private Procedures procedures;

    /**
     * The stay during which the patient undergoes the procedure.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Stay", referencedColumnName = "StayID", insertable = false, updatable = false)
    private Stay stay;

    /**
     * The physician performing the procedure on the patient.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Physician", referencedColumnName = "EmployeeID", insertable = false, updatable = false)
    private Physician physician;

    /**
     * The assisting nurse involved in the procedure.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AssistingNurse", referencedColumnName = "EmployeeID", insertable = false, updatable = false)
    private Nurse assistingNurse;

}
