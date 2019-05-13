package com.proyectDAO.emergencyCare.dao;

import java.util.List;

import com.proyectDAO.emergencyCare.model.Supply;

public interface ISupplyDAO {
	
	public void save(Supply entity);
	public void update(Supply entity);
	public void delete(Supply entity);
	public Supply findById(Long consecutive);
	public List<Supply> findAll();
	public List<Supply> findBetweenAmounts(Integer minAmount, Integer maxAmount);
	
}
