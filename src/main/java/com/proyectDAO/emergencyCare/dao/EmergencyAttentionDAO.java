package com.proyectDAO.emergencyCare.dao;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.proyectDAO.emergencyCare.model.EmergencyAttention;
import com.proyectDAO.emergencyCare.model.Patient;

@Repository
@Scope("singleton")
public class EmergencyAttentionDAO implements IEmergencyAttentionDAO{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void save(EmergencyAttention entity) {
		entityManager.persist(entity);
	}

	@Override
	public void update(EmergencyAttention entity) {
		entityManager.merge(entity);
	}

	@Override
	public void delete(EmergencyAttention entity) {
		entityManager.remove(entity);
	}

	@Override
	public EmergencyAttention findById(Long consecutive) {
		return entityManager.find(EmergencyAttention.class, consecutive);
	}

	@Override
	public List<EmergencyAttention> findAll() {
		TypedQuery<EmergencyAttention> jpql = entityManager.createQuery("Select a from EmergencyAttention a", EmergencyAttention.class);
		return jpql.getResultList();
	}

	@Override
	public List<EmergencyAttention> findBetweenDates(LocalDate init, LocalDate end) {
		TypedQuery<EmergencyAttention> jpql = entityManager.createQuery("select a from EmergencyAttention a where a.date between :init and :end", EmergencyAttention.class);
		jpql.setParameter("init", init);
		jpql.setParameter("end", end);
		return jpql.getResultList();
	}
	
	@Override
	public List<Object> findPatientsCountAttentionsOrderedByDocument() {
		return entityManager.createQuery("select a.patient, count (a) from EmergencyAttention a group by a.patient order by a.patient.document").getResultList();
	}
	
	@Override
	public List<Patient> findPatientsMoreThanTwoAttentionLastMonth() {
		LocalDate now = LocalDate.now();
		LocalDate limit = now.minusDays(30);
		TypedQuery<Patient> jpql = entityManager.createQuery("select a.patient from EmergencyAttention a where a.date between :limit and :init group by a.patient having count(a) > 2", Patient.class);
		jpql.setParameter("init", now);
		jpql.setParameter("limit", limit);
		return jpql.getResultList();
	}
	
}
