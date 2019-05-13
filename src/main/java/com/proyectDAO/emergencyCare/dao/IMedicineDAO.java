package com.proyectDAO.emergencyCare.dao;

import java.util.List;

import com.proyectDAO.emergencyCare.model.Medicine;

public interface IMedicineDAO {
	
	public void save(Medicine entity);
	public void update(Medicine entity);
	public void delete(Medicine entity);
	public Medicine findById(Long consecutive);
	public List<Medicine> findAll();

}
