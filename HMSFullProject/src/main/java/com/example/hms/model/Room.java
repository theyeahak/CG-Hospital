package com.example.hms.model;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a room in the hospital management system.
 * This class contains information about the room's number, type, availability,
 * and its association with a block and patients staying in it.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Room")
public class Room {

    /**
     * Unique identifier for the room.
     */
    @Id
    @Column(name = "RoomNumber")
    private Integer roomNumber;

    /**
     * Type of the room (e.g., Single, Double, ICU).
     */
    @Column(nullable = false, length = 30, name = "RoomType")
    private String roomType;

    /**
     * The block to which this room belongs.
     * This relationship is bidirectional with the `Block` entity.
     */
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "BlockFloor", referencedColumnName = "BlockFloor"),
        @JoinColumn(name = "BlockCode", referencedColumnName = "BlockCode")
    })
    @JsonIgnore
    private Block block;

    /**
     * List of stays in this room.
     * This relationship is bidirectional with the `Stay` entity.
     */
    @OneToMany(mappedBy = "room")
    @JsonIgnore
    private List<Stay> stays;

    /**
     * Indicates whether the room is available for use.
     * If the room is unavailable, it cannot be assigned to patients.
     */
    @Column(nullable = false, name = "Unavailable")
    private Boolean unavailable;

    /**
     * Custom string representation of the room.
     * @return A string representation of the room, including the room number, type, and availability status.
     */
    
}
