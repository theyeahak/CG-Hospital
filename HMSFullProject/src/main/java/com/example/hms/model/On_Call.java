package com.example.hms.model;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing the "On_Call" table in the database.
 * This class stores information about on-call shifts for nurses, including the nurse assigned to the shift and the block in which the shift occurs.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "On_Call")
public class On_Call {

    /**
     * The composite key for the On_Call entity.
     * This key consists of the nurse's employee ID and the block's floor and code.
     */
    @EmbeddedId
    private OnCallId id;

    /**
     * The nurse assigned to the on-call shift.
     * This is a many-to-one relationship with the Nurse entity.
     * The nurse is referenced by the "EmployeeID" field.
     * The relationship is fetched lazily to optimize performance.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Nurse", referencedColumnName = "EmployeeID", insertable = false, updatable = false)
    private Nurse nurse;

    /**
     * The block where the on-call shift takes place.
     * This is a many-to-one relationship with the Block entity.
     * The relationship is fetched lazily, and it is mapped by both "BlockFloor" and "BlockCode" fields.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "BlockFloor", referencedColumnName = "BlockFloor", insertable = false, updatable = false),
        @JoinColumn(name = "BlockCode", referencedColumnName = "BlockCode", insertable = false, updatable = false)
    })
    private Block block;

    /**
     * Converts the object to a string representation.
     * 
     * @return a string representation of the On_Call entity, including the ID.
     */
    
}

