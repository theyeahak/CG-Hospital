package com.example.hms.model;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Represents a medical procedure in the hospital management system.
 * This class contains the details of the procedure including its code, name, cost,
 * and the associated records of patients undergoing the procedure and physicians trained in it.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Procedures")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Procedures {

    /**
     * Unique identifier for the procedure.
     */
    @Id
    private Integer code;

    /**
     * Name of the procedure.
     */
    @Column(nullable = false, length = 30)
    private String name;

    /**
     * Cost associated with the procedure.
     */
    @Column(nullable = false)
    private Double cost;

    /**
     * List of records indicating which patients have undergone this procedure.
     * This relationship is bidirectional, with the `Undergoes` entity.
     */
    @OneToMany(mappedBy = "procedures")
    @JsonIgnore
    private List<Undergoes> undergoes;

    /**
     * List of records indicating which physicians are trained in performing this procedure.
     * This relationship is bidirectional, with the `Trained_In` entity.
     */
    @OneToMany(mappedBy = "treatment")
    @JsonIgnore
    private List<Trained_In> trainedIns;


	public Procedures(int code, String name, double cost) {
		this.code=code;
		this.name=name;
		this.cost=cost;
		
	}
}
