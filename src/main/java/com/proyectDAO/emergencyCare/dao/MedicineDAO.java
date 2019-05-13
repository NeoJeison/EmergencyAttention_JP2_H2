package com.proyectDAO.emergencyCare.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.proyectDAO.emergencyCare.model.Medicine;

@Repository
@Scope("singleton")
public class MedicineDAO implements IMedicineDAO{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void save(Medicine entity) {
		entityManager.persist(entity);
	}

	@Override
	public void update(Medicine entity) {
		entityManager.merge(entity);
	}

	@Override
	public void delete(Medicine entity) {
		entityManager.remove(entity);	
	}

	@Override
	public Medicine findById(Long consecutive) {
		return entityManager.find(Medicine.class, consecutive);
	}

	@Override
	public List<Medicine> findAll() {
		TypedQuery<Medicine> jpql = entityManager.createQuery("Select a from Medicine a", Medicine.class);
		return jpql.getResultList();
	}

}
