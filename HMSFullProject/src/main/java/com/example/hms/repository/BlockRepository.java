package com.example.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hms.model.Block;
import com.example.hms.model.BlockId;

@Repository
public interface BlockRepository extends JpaRepository<Block, BlockId> {
	/**
	 * Repository interface for managing block entities.
	 */
}
