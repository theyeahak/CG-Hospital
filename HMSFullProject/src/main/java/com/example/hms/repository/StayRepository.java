package com.example.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hms.model.Stay;

@Repository
public interface StayRepository extends JpaRepository<Stay, Integer> {

}
