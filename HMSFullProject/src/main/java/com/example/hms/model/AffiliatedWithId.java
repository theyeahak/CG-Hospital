package com.example.hms.model;
import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.*;

/**
 * Composite key class for the "affiliated_with" entity.
 * This class represents the composite primary key consisting of
 * physician and department IDs.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Embeddable
public class AffiliatedWithId implements Serializable {

    /**
     * The physician's ID associated with the affiliated_with record.
     */
    public Integer physician;

    /**
     * The department's ID associated with the affiliated_with record.
     */
    public Integer department;
}
