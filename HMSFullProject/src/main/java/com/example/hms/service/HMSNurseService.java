package com.example.hms.service;

import java.util.List;
import com.example.hms.model.Nurse;
public interface HMSNurseService 
{
	public List<Nurse> getAll();
	public Nurse getById(int empid);
	public String getPosById(int empid);
	public boolean findRegister(int empid);
	public Nurse saveNurse(Nurse nurse);
	public Nurse updateNurseRegistrationStatus(int empid, Nurse n);
	public Nurse updateSSN(int empid,Nurse n);
}
