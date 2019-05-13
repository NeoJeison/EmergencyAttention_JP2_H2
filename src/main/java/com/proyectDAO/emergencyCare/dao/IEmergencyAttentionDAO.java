package com.proyectDAO.emergencyCare.dao;

import java.time.LocalDate;
import java.util.List;

import com.proyectDAO.emergencyCare.model.EmergencyAttention;
import com.proyectDAO.emergencyCare.model.Patient;

public interface IEmergencyAttentionDAO {
	
	public void save(EmergencyAttention entity);
	public void update(EmergencyAttention entity);
	public void delete(EmergencyAttention entity);
	public EmergencyAttention findById(Long consecutive);
	public List<EmergencyAttention> findAll();
	public List<EmergencyAttention> findBetweenDates(LocalDate init, LocalDate end);
	public List<Object> findPatientsCountAttentionsOrderedByDocument();
	public List<Patient> findPatientsMoreThanTwoAttentionLastMonth();
}
