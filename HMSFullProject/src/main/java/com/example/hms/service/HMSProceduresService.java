package com.example.hms.service;

import java.util.List;

import com.example.hms.exception.AlreadyFoundException;
import com.example.hms.exception.ProcedureIdNotFoundException;
import com.example.hms.model.Procedures;

public interface HMSProceduresService
{
	
	//ranjjan
	public Procedures addProcedure(Procedures procedure)throws AlreadyFoundException ;
	public Procedures updateProcedureCost(Integer id, Double newCost)throws ProcedureIdNotFoundException;
	public Procedures updateProcedureName(Integer id, String newName);
	public List<Procedures> getAllProcedures();
	public Procedures getProcedureByName(String procedureName);
	public Procedures getProceduresById(int procedureId);
	
	
	
	
	
}
