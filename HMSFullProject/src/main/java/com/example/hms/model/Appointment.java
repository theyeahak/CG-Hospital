package com.example.hms.model;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing the "Appointment" table in the database.
 * This class stores information about appointments scheduled for patients.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Appointment")
public class Appointment {

    /**
     * The unique ID for the appointment.
     */
    @Id
    @Column(name = "AppointmentID")
    private Integer appointmentID;

    /**
     * The patient associated with the appointment.
     * This is a foreign key referencing the patient's "ssn" in the Patient table.
     */
    @ManyToOne
    @JoinColumn(name = "Patient", referencedColumnName = "ssn")
    private Patient patient;

    /**
     * The nurse preparing the appointment.
     * This is a foreign key referencing the nurse's "employeeID".
     */
    @ManyToOne
    @JoinColumn(name = "PrepNurse", referencedColumnName = "employeeID")
    private Nurse prepNurse;

    /**
     * The physician conducting the appointment.
     * This is a foreign key referencing the physician's "employeeID".
     */
    @ManyToOne
    @JoinColumn(name = "Physician", referencedColumnName = "employeeID")
    private Physician physician;

    /**
     * The start time of the appointment.
     * Cannot be null.
     */
    @Column(nullable = false, name = "Starto")
    private LocalDateTime start;

    /**
     * The end time of the appointment.
     * Cannot be null.
     */
    @Column(nullable = false, name = "Endo")
    private LocalDateTime end;

    /**
     * The room where the examination is scheduled to take place.
     * Cannot be null.
     */
    @Column(nullable = false, name = "ExaminationRoom")
    private String examinationRoom;

    /**
     * The list of prescriptions associated with the appointment.
     * This is a one-to-many relationship mapped by the "appointment" field in the Prescribes entity.
     * Cascade type is set to ALL, and it is ignored in JSON responses.
     */
    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Prescribes> prescriptions;

    /**
     * Converts the object to a string representation.
     * 
     * @return a string representation of the appointment, excluding relationships.
     */
   
}
