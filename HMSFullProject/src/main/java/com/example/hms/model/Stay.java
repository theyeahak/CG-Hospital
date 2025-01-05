package com.example.hms.model;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a stay of a patient in a hospital room.
 * This class tracks the patient's stay in a particular room, including start and end times,
 * and the relationship with the patient and room entities.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Stay")
public class Stay {

    /**
     * Unique identifier for the stay record.
     */
    @Id
    @Column(name = "StayID")
    private Integer stayID;

    /**
     * The patient associated with this stay.
     * This is a many-to-one relationship with the `Patient` entity.
     */
    @ManyToOne
    @JoinColumn(name = "Patient", referencedColumnName = "SSN", foreignKey = @ForeignKey(name = "fk_Stay_Patient_SSN"), nullable = false)
    private Patient patient;

    /**
     * The room associated with this stay.
     * This is a many-to-one relationship with the `Room` entity.
     */
    @ManyToOne
    @JoinColumn(name = "Room", referencedColumnName = "RoomNumber", foreignKey = @ForeignKey(name = "fk_Stay_Room_Number"), nullable = false)
    private Room room;

    /**
     * The start date and time of the patient's stay.
     */
    @Column(name = "StayStart", nullable = false)
    private LocalDateTime stayStart;

    /**
     * The end date and time of the patient's stay.
     */
    @Column(name = "StayEnd", nullable = false)
    private LocalDateTime stayEnd;

    /**
     * List of undergoes related to this stay.
     * This relationship is bidirectional with the `Undergoes` entity.
     */
    @OneToMany(mappedBy = "stay")
    private List<Undergoes> undergoes;

    /**
     * Custom string representation of the stay record.
     * @return A string representation of the stay, including the stay ID, start and end times.
     */
    
}
