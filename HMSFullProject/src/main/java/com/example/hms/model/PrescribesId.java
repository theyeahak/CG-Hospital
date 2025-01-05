package com.example.hms.model;
import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Represents the composite key for the Prescribes entity.
 * The composite key consists of the physician's ID, patient's SSN, medication code,
 * and the date the prescription was issued.
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class PrescribesId implements Serializable {

    /**
     * The ID of the physician who issued the prescription.
     */
    private Integer physician;

    /**
     * The SSN of the patient receiving the prescription.
     */
    private Integer patient;

    /**
     * The code of the medication being prescribed.
     */
    private Integer medication;

    /**
     * The date when the prescription was issued.
     */
    private LocalDateTime date;

}

