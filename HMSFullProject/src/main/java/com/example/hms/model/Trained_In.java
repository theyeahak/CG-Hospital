package com.example.hms.model;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a relationship between a physician and the medical procedures they are trained in.
 * This class tracks the certification date and expiration for a physician's training in a specific procedure.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Trained_In")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Trained_In {

    /**
     * Composite primary key that uniquely identifies the relationship between a physician and a treatment.
     */
    @EmbeddedId
    private TrainedInId id;

    /**
     * The physician who is trained in the treatment.
     * This relationship is many-to-one with the `Physician` entity.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Physician", referencedColumnName = "EmployeeID", insertable = false, updatable = false)
    @JsonIgnore
    private Physician physician;

    /**
     * The medical procedure the physician is trained in.
     * This relationship is many-to-one with the `Procedures` entity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Treatment", referencedColumnName = "Code", insertable = false, updatable = false)
    @JsonIgnore
    private Procedures treatment;

    /**
     * The date the physician's certification for this treatment was awarded.
     */
    @Column(name = "CertificationDate", nullable = false)
    private LocalDateTime certificationDate;

    /**
     * The expiration date of the physician's certification for this treatment.
     */
    @Column(name = "CertificationExpires", nullable = false)
    private LocalDateTime certificationExpires;

    /**
     * Custom string representation of the `Trained_In` relationship.
     * @return A string representation of the trained-in relationship, including certification dates.
     */
    @Override
    public String toString() {
        return "Trained_In [id=" + id + ", certificationDate=" + certificationDate + ", certificationExpires="
                + certificationExpires + "]";
    }
}
