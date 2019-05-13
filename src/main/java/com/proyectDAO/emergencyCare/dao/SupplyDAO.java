package com.proyectDAO.emergencyCare.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.proyectDAO.emergencyCare.model.Supply;

@Repository
@Scope("singleton")
public class SupplyDAO implements ISupplyDAO{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void save(Supply entity) {
		entityManager.persist(entity);		
	}

	@Override
	public void update(Supply entity) {
		entityManager.merge(entity);		
	}

	@Override
	public void delete(Supply entity) {
		entityManager.remove(entity);		
	}

	@Override
	public Supply findById(Long consecutive) {
		return entityManager.find(Supply.class, consecutive);
	}

	@Override
	public List<Supply> findAll() {
		TypedQuery<Supply> jpql = entityManager.createQuery("Select a from Supply a", Supply.class);
		return jpql.getResultList();
	}

	@Override
	public List<Supply> findBetweenAmounts(Integer minAmount, Integer maxAmount) {
		TypedQuery<Supply> jpql = entityManager.createQuery("Select a from Supply a where a.amount between :minAmount and :maxAmount", Supply.class);
		jpql.setParameter("minAmount", minAmount);
		jpql.setParameter("maxAmount", maxAmount);
		return jpql.getResultList();
	}

}
