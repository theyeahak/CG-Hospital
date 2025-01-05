package com.example.hms.model;
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
import lombok.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing the "Department" table in the database.
 * This class stores information about departments within the hospital, including the department's name and head physician.
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Department")
public class Department {

    /**
     * The unique ID for the department.
     */
    @Id
    @Column(name = "DepartmentID")
    private Integer departmentID;

    /**
     * The name of the department.
     * Cannot be null and has a maximum length of 30 characters.
     */
    @Column(nullable = false, length = 30, name = "Name")
    private String name;

    /**
     * The physician who heads the department.
     * This is a foreign key referencing the "employeeID" of a physician.
     */
    @ManyToOne
    @JoinColumn(name = "Head")
    private Physician head;

    /**
     * The list of affiliations associated with the department.
     * This is a one-to-many relationship mapped by the "department" field in the Affiliated_With entity.
     * Cascade type is set to ALL, and it is ignored in JSON responses.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "department")
    @JsonIgnore
    private List<Affiliated_With> affiliations;

    /**
     * Converts the object to a string representation.
     * 
     * @return a string representation of the department, including its ID and name.
     */
    @Override
    public String toString() {
        return "Department [departmentID=" + departmentID + ", name=" + name + "]";
    }

	public Department(int departmentID, String name, Physician head) {
		this.departmentID=departmentID;
		this.name=name;
		this.head=head;
		
	}

	public Department(int departmentID, String name) {
		this.departmentID=departmentID;
		this.name=name;
	}
	
	public Integer getId() {
		return null;
	}
}
