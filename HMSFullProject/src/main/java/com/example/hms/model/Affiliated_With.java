package com.example.hms.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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
 * Entity class representing the "affiliated_with" table in the database.
 * This class is used to manage the relationship between physicians and departments.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "affiliated_with")
public class Affiliated_With {

    /**
     * Composite primary key for the affiliated_with entity.
     */
    @EmbeddedId
    private AffiliatedWithId id;

    /**
     * The physician associated with this record.
     * Uses a foreign key reference to the "Physician" column in the "employeeID" column of the physician entity.
     */
    @ManyToOne
    @MapsId("physician")
    @JoinColumn(name = "Physician", referencedColumnName = "employeeID")
    private Physician physician;

    /**
     * The department associated with this record.
     * Uses a foreign key reference to the "Department" column in the "departmentID" column of the department entity.
     */
    @ManyToOne
    @MapsId("department")
    @JoinColumn(name = "Department", referencedColumnName = "departmentID")
    private Department department;

    /**
     * Indicates whether this is the primary affiliation for the physician with the department.
     * Non-nullable column in the database.
     */
    @Column(nullable = false, name = "PrimaryAffiliation")
    private Boolean primaryAffiliation;

    
}
