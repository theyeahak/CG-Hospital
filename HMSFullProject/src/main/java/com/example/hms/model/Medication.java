package com.example.hms.model;

import java.util.List;

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
 * Entity class representing the "Medication" table in the database.
 * This class stores information about medications prescribed to patients, including the medication code, name, brand, and description.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Medication")
public class Medication {

    /**
     * The unique code for the medication.
     */
    @Id
    @Column(name = "Code")
    private Integer code;

    /**
     * The name of the medication.
     * Cannot be null and has a maximum length of 30 characters.
     */
    @Column(nullable = false, length = 30, name = "Name")
    private String name;

    /**
     * The brand of the medication.
     * Cannot be null and has a maximum length of 30 characters.
     */
    @Column(nullable = false, length = 30, name = "Brand")
    private String brand;

    /**
     * A brief description of the medication.
     * Cannot be null and has a maximum length of 30 characters.
     */
    @Column(nullable = false, length = 30, name = "Description")
    private String description;
    
    /**
     * The list of prescriptions associated with the medication.
     * This is a one-to-many relationship mapped by the "medication" field in the Prescribes entity.
     * Cascade type is set to ALL.
     */
    @OneToMany(mappedBy = "medication", cascade = CascadeType.ALL)
    private List<Prescribes> prescriptions;

    /**
     * Converts the object to a string representation.
     * 
     * @return a string representation of the medication, including its code, name, brand, and description.
     */
    
}

    

