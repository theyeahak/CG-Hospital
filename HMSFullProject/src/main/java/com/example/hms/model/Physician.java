package com.example.hms.model;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Physician entity in the hospital management system.
 * A physician is a medical professional responsible for diagnosing and treating patients.
 * This class contains the physician's personal details and their associations with various hospital entities such as departments, appointments, patients, and more.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Physician")
public class Physician {

    /**
     * The unique identifier for the physician (Employee ID).
     */
    @Id
    @Column(name = "EmployeeID")
    private Integer employeeID;

    /**
     * The name of the physician.
     */
    @Column(nullable = false, length = 30, name = "Name")
    private String name;

    /**
     * The position or specialization of the physician (e.g., surgeon, cardiologist).
     */
    @Column(nullable = false, length = 30, name = "Position")
    private String position;

    /**
     * The physician's Social Security Number (SSN).
     */
    @Column(nullable = false, name = "SSN")
    private Integer ssn;
    
    /**
     * A list of departments that the physician is head of.
     * This is a one-to-many relationship with the Department entity.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "head")
    @JsonIgnore
    private List<Department> departments;
    
    /**
     * A list of affiliations between the physician and various departments.
     * This is a one-to-many relationship with the Affiliated_With entity.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "physician")
    @JsonIgnore
    private List<Affiliated_With> affiliations;
    
    /**
     * A list of patients assigned to the physician as their primary care provider (PCP).
     * This is a one-to-many relationship with the Patient entity.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pcp")
    @JsonIgnore
    private List<Patient> patients;
    
    /**
     * A list of appointments where the physician is the attending doctor.
     * This is a one-to-many relationship with the Appointment entity.
     */
    @OneToMany(mappedBy = "physician", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Appointment> appointments;
    
    /**
     * A list of prescriptions written by the physician.
     * This is a one-to-many relationship with the Prescribes entity.
     */
    @OneToMany(mappedBy = "physician", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Prescribes> prescriptions;
    
    /**
     * A list of medical procedures that the physician has performed.
     * This is a one-to-many relationship with the Undergoes entity.
     */
    @OneToMany(mappedBy = "physician")
    @JsonIgnore
    private List<Undergoes> undergoes;
    
    /**
     * A list of training records for the physician in various medical fields.
     * This is a one-to-many relationship with the Trained_In entity.
     */
    @OneToMany(mappedBy = "physician")
    @JsonIgnore
    private List<Trained_In> trainedIns;
    
    /**
     * Generates a string representation of the Physician object.
     * 
     * @return A string containing the physician's employee ID, name, position, and SSN.
     */
    

	public Physician(int employeeID, String name, String position, int ssn) {
		this.employeeID=employeeID;
		this.name=name;
		this.position=position;
		this.ssn=ssn;
	}
}
