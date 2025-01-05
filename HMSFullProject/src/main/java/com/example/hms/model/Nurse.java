package com.example.hms.model;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity class representing the "Nurse" table in the database.
 * This class stores information about nurses, including their employee ID, name, position, registration status, and SSN.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "Nurse")
public class Nurse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "EmployeeID")
    private Integer employeeID;
 
    @Column(nullable = false, length = 30, name = "Name")
    private String name;
 
    @Column(nullable = false, length = 30, name = "Position")
    private String position;
 
    @Column(nullable = false, name = "Registered")
    private Boolean registered;
 
    @Column(nullable = false, name = "SSN")
    private Integer ssn;
    @OneToMany(mappedBy = "prepNurse", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Appointment> appointments;
    @OneToMany(mappedBy = "nurse", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<On_Call> onCalls;
    @OneToMany(mappedBy = "assistingNurse")
    @JsonIgnore
    private List<Undergoes> undergoes;
}

