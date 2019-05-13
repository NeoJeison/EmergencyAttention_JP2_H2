package com.proyectDAO.emergencyCare.dao;

import java.util.List;

import com.proyectDAO.emergencyCare.model.Patient;

public interface IPatientDAO {
	
	public void save(Patient entity);
	public void update(Patient entity);
	public void delete(Patient entity);
	public Patient findByDocument(String document);
	public List<Patient> findByName(String name);
	public List<Patient> findByLastNames(String lastNames);
	public List<Patient> findAll();
	
}
