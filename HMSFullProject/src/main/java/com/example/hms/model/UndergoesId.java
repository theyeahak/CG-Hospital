package com.example.hms.model;
import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Represents the composite primary key for the `Undergoes` entity. 
 * This class is used to uniquely identify the relationship between a patient, 
 * the procedure they undergo, the associated hospital stay, and the time of the procedure.
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Embeddable
public class UndergoesId implements Serializable {

    /**
     * The patient's unique identifier (SSN) associated with the medical procedure.
     */
    @Column(name = "Patient")
    private Integer patient;

    /**
     * The unique identifier for the procedure the patient undergoes.
     */
    @Column(name = "Procedures")
    private Integer procedures;

    /**
     * The unique identifier for the hospital stay during which the procedure takes place.
     */
    @Column(name = "Stay")
    private Integer stay;

    /**
     * The date and time when the patient undergoes the procedure.
     */
    @Column(name = "DateUndergoes")
    private LocalDateTime dateUndergoes;
}

