package com.proyectDAO.emergencyCare.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.proyectDAO.emergencyCare.model.Medicine;
import com.proyectDAO.emergencyCare.model.MedicineInventory;

@Repository
@Scope("singleton")
public class MedicineInventoryDAO implements IMedicineInventoryDAO{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void save(MedicineInventory entity) {
		entityManager.persist(entity);		
	}

	@Override
	public void update(MedicineInventory entity) {
		entityManager.merge(entity);		
	}

	@Override
	public void delete(MedicineInventory entity) {
		entityManager.remove(entity);		
	}

	@Override
	public MedicineInventory findById(Long consecutive) {
		return entityManager.find(MedicineInventory.class, consecutive);
	}

	@Override
	public List<MedicineInventory> findAll() {
		TypedQuery<MedicineInventory> jpql = entityManager.createQuery("Select a from MedicineInventory a", MedicineInventory.class);
		return jpql.getResultList();
	}
	
	@Override
	public List<Medicine> findMedsWithInventoryLessThan10() {
		TypedQuery<Medicine> jpql = entityManager.createQuery("select a.medicine from MedicineInventory a group by a.medicine having sum(a.quantityAvailable) < 10", Medicine.class);
		return jpql.getResultList();
	}

}
