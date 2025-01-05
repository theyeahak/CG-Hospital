package com.example.hms.model;
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Composite primary key class used to uniquely identify a `Trained_In` relationship.
 * This class represents the combination of a physician and a treatment they are trained in.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Embeddable
public class TrainedInId implements Serializable {

    /**
     * The physician who is trained in the treatment.
     */
    @Column(name = "Physician")
    private Integer physician;

    /**
     * The treatment (procedure) for which the physician is trained.
     */
    @Column(name = "Treatment")
    private Integer treatment;
}
