package com.example.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hms.model.OnCallId;
import com.example.hms.model.On_Call;

@Repository
public interface On_Call_Repository extends JpaRepository<On_Call, OnCallId>{

}
