package com.proyectDAO.emergencyCare.dao;

import java.util.List;

import com.proyectDAO.emergencyCare.model.Medicine;
import com.proyectDAO.emergencyCare.model.MedicineInventory;

public interface IMedicineInventoryDAO {
	
	public void save(MedicineInventory entity);
	public void update(MedicineInventory entity);
	public void delete(MedicineInventory entity);
	public MedicineInventory findById(Long consecutive);
	public List<MedicineInventory> findAll();
	public List<Medicine> findMedsWithInventoryLessThan10();
	
}
