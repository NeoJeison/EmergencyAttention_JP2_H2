package com.proyectDAO.emergencyCare.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.proyectDAO.emergencyCare.model.Patient;

@Repository
@Scope("singleton")
public class PatientDAO implements IPatientDAO{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void save(Patient entity) {
		entityManager.persist(entity);
	}

	@Override
	public void update(Patient entity) {
		entityManager.merge(entity);
	}

	@Override
	public void delete(Patient entity) {
		entityManager.remove(entity);		
	}

	@Override
	public Patient findByDocument(String document) {
		return entityManager.find(Patient.class, document);
	}

	@Override
	public List<Patient> findAll() {
		TypedQuery<Patient> jpql = entityManager.createQuery("Select a from Patient a", Patient.class);
		return jpql.getResultList();
	}

	@Override
	public List<Patient> findByName(String name) {
		TypedQuery<Patient> jpql = entityManager.createQuery("Select a from Patient a where a.name = :name", Patient.class);
		jpql.setParameter("name", name);
		return jpql.getResultList();
	}

	@Override
	public List<Patient> findByLastNames(String lastNames) {
		TypedQuery<Patient> jpql = entityManager.createQuery("Select a from Patient a where a.lastNames = :lastNames", Patient.class);
		jpql.setParameter("lastNames", lastNames);
		return jpql.getResultList();
	}

}
