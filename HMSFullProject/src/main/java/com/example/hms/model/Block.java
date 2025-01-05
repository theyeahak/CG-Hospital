package com.example.hms.model;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing the "Block" table in the database.
 * This class stores information about hospital blocks and their associated rooms and on-call staff.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Block")
public class Block {

    /**
     * Composite primary key for the block entity.
     */
    @EmbeddedId
    private BlockId id;

    /**
     * The list of rooms associated with this block.
     * This is a one-to-many relationship mapped by the "block" field in the Room entity.
     */
    @OneToMany(mappedBy = "block")
    private List<Room> rooms;

    /**
     * The list of on-call staff associated with this block.
     * This is a one-to-many relationship mapped by the "block" field in the On_Call entity.
     */
    @OneToMany(mappedBy = "block")
    private List<On_Call> onCalls;

    /**
     * Converts the object to a string representation.
     * 
     * @return a string representation of the block, including its id.
     */
    
}


