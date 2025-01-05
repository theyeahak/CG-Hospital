package com.example.hms.model;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Embeddable class representing the composite primary key for the "On_Call" entity.
 * This class contains the fields required to uniquely identify an on-call shift, which includes the nurse, block, and the start and end times of the on-call shift.
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Embeddable
public class OnCallId {

    /**
     * The ID of the nurse assigned to the on-call shift.
     * This is a reference to the "EmployeeID" field in the Nurse entity.
     */
    @Column(name = "Nurse")
    private Integer nurse;

    /**
     * The floor of the block where the on-call shift takes place.
     */
    @Column(name = "BlockFloor")
    private Integer blockFloor;

    /**
     * The code of the block where the on-call shift takes place.
     */
    @Column(name = "BlockCode")
    private Integer blockCode;

    /**
     * The start time of the on-call shift.
     */
    @Column(name = "OnCallStart")
    private LocalDateTime onCallStart;

    /**
     * The end time of the on-call shift.
     */
    @Column(name = "OnCallEnd")
    private LocalDateTime onCallEnd;
}
