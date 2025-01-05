package com.example.hms.model;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Composite key class for the "Block" entity.
 * This class represents the composite primary key consisting of block floor and block code.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Embeddable
public class BlockId {

    /**
     * The floor of the block, part of the composite primary key.
     */
    @Column(name = "BlockFloor")
    private Integer blockFloor;

    /**
     * The unique code of the block, part of the composite primary key.
     */
    @Column(name = "BlockCode")
    private Integer blockCode;
}
