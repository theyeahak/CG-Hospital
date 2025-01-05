package com.example.hms.model;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Represents a Patient entity in the hospital management system.
 * This class contains information about a patient including their personal details,
 * primary care physician, appointments, prescriptions, hospital stays, and undergone procedures.
 */
@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Patient")
public class Patient {


	/**
     * The patient's unique identifier, which is their Social Security Number (SSN).
     */
    @Id
    @Column(name = "SSN")
    private Integer ssn;
    
    /**
     * The name of the patient.
     */
    @Column(nullable = false, length = 30, name = "Name")
    private String name;
    
    /**
     * The address of the patient.
     */
    @Column(nullable = false, length = 30, name = "Address")
    private String address;
    
    /**
     * The phone number of the patient.
     */
    @Column(nullable = false, length = 30, name = "Phone")
    private String phone;

    /**
     * The insurance ID of the patient.
     */
    @Column(nullable = false, name = "InsuranceID")
    private Integer insuranceID;
    
    /**
     * The primary care physician (PCP) assigned to the patient.
     * This is a many-to-one relationship with the Physician entity.
     */
    @ManyToOne
    @JoinColumn(name = "PCP", referencedColumnName = "employeeID")
    private Physician pcp;
    
    /**
     * A list of appointments associated with the patient.
     * This is a one-to-many relationship with the Appointment entity.
     */
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Appointment> appointments;
    
    /**
     * A list of prescriptions issued to the patient.
     * This is a one-to-many relationship with the Prescribes entity.
     */
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Prescribes> prescriptions;
    
    /**
     * A list of hospital stays of the patient.
     * This is a one-to-many relationship with the Stay entity.
     */
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Stay> stays;
    
    /**
     * A list of medical procedures that the patient has undergone.
     * This is a one-to-many relationship with the Undergoes entity.
     */
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Undergoes> undergoes;

    /**
     * Generates a string representation of the Patient object.
     * 
     * @return A string containing the patient's SSN, name, address, phone, insurance ID, and primary care physician (PCP).
     */
    public Patient(int ssn, String name, String address) {
		this.ssn=ssn;
		this.name=name;
		this.address=address;
	}
    
}
