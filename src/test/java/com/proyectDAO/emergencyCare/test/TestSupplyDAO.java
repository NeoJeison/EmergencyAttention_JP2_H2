package com.proyectDAO.emergencyCare.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.proyectDAO.emergencyCare.dao.IMedicineDAO;
import com.proyectDAO.emergencyCare.dao.IPatientDAO;
import com.proyectDAO.emergencyCare.dao.ISupplyDAO;
import com.proyectDAO.emergencyCare.model.Medicine;
import com.proyectDAO.emergencyCare.model.Patient;
import com.proyectDAO.emergencyCare.model.State;
import com.proyectDAO.emergencyCare.model.Supply;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Rollback(false)
public class TestSupplyDAO {
	
	@Autowired
	private ISupplyDAO supplyDAO;
	
	@Autowired
	private IMedicineDAO medicineDAO;
	
	@Autowired
	private IPatientDAO patientDAO;
	
	private Medicine medicine;
	
	private Patient patient;
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void setUpData() {
		medicine = new Medicine();
		medicine.setName("Acetaminofen");
		medicine.setGenericName("Acetaminofen");
		medicine.setLaboratory("JGB");
		medicine.setTypeOfAdministration("Oral");
		medicine.setIndicAndContraindic("None");
		medicineDAO.save(medicine);
		
		patient = new Patient();
		patient.setDocument("0001");
		patient.setName("Jeison");
		patient.setLastNames("Mejia");
		patient.setState(State.Active);
		patient.setAcademicProgram("Software engineering");
		patient.setAcademicDependence("None");
		patientDAO.save(patient);
	}
	
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test01Save() {
		
		setUpData();
		
		assertNotNull(supplyDAO);
		
		Long consecutive = new Long(2);
		
		Supply supply = new Supply();
		supply.setMedicine(medicine);
		supply.setAmount(5);
		supply.setPatient(patient);
		supply.setDate(LocalDate.of(2019, 6, 15));
		supply.setTime(LocalTime.of(9, 15));
		supply.setObservation("None");
		supply.setPathology("Broken leg");
		
		supplyDAO.save(supply);
		
		assertEquals(supply, supplyDAO.findById(consecutive));
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test02Update() {
		
		assertNotNull(supplyDAO);
		
		Long consecutive = new Long(2);
		
		Supply supply = supplyDAO.findById(consecutive);
		
		assertNotNull(supply);
		
		LocalDate ld = LocalDate.of(2019, 5, 16);
		String pathology = "Broken legs";
		supply.setDate(ld);
		supply.setPathology(pathology);
		
		supplyDAO.update(supply);
		
		assertEquals(ld, supplyDAO.findById(consecutive).getDate());
		assertEquals(pathology, supplyDAO.findById(consecutive).getPathology());
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test03Delete() {
		
		assertNotNull(supplyDAO);
		
		Long consecutive = new Long(2);
		
		Supply supply = supplyDAO.findById(consecutive);
		
		assertNotNull(supply);
		
		supplyDAO.delete(supply);
		
		assertNull("The supply was not deleted", supplyDAO.findById(consecutive));
		
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void setUpDAO() {
		
		Supply supply1 = new Supply();
		supply1.setMedicine(medicine);
		supply1.setAmount(5);
		supply1.setPatient(patient);
		supply1.setDate(LocalDate.of(2019, 6, 15));
		supply1.setTime(LocalTime.of(9, 15));
		supply1.setObservation("None");
		supply1.setPathology("Broken leg");
		
		supplyDAO.save(supply1);
		
		Supply supply2 = new Supply();
		supply2.setMedicine(medicine);
		supply2.setAmount(10);
		supply2.setPatient(patient);
		supply2.setDate(LocalDate.of(2019, 6, 16));
		supply2.setTime(LocalTime.of(10, 15));
		supply2.setObservation("None");
		supply2.setPathology("Broken arm");
		
		supplyDAO.save(supply2);
		
		Supply supply3 = new Supply();
		supply3.setMedicine(medicine);
		supply3.setAmount(3);
		supply3.setPatient(patient);
		supply3.setDate(LocalDate.of(2019, 6, 17));
		supply3.setTime(LocalTime.of(11, 15));
		supply3.setObservation("None");
		supply3.setPathology("Broken neck");
		
		supplyDAO.save(supply3);
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test04FindAll() {
		
		setUpDAO();
		
		assertNotNull(supplyDAO);
		
		List<Supply> listOfSupplies = supplyDAO.findAll();
		
		assertNotNull(listOfSupplies);
		
		assertEquals("DAO is suppose to have three elements", 3, listOfSupplies.size());
		
		Long consecutive = new Long(5);
		
		Supply supply= supplyDAO.findById(consecutive);
		
		supplyDAO.delete(supply);
		
		assertNull("The supply was not deleted", supplyDAO.findById(consecutive));
		
		listOfSupplies = supplyDAO.findAll();
		
		assertEquals("DAO is suppose to have two elements", 2, listOfSupplies.size());
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test05FindByConsecutive() {
		
		assertNotNull(supplyDAO);
		
		Long consecutive = new Long(3);
		
		Supply supplyById = supplyDAO.findById(consecutive);
		
		assertNotNull(supplyById);
		
		assertEquals("Medicine is not correct", medicine, supplyById.getMedicine());
		assertEquals("Amount is not correct", new Integer(5), supplyById.getAmount());
		assertEquals("Patient is not correct", patient, supplyById.getPatient());
		assertEquals("Date is not correct", LocalDate.of(2019, 6, 15), supplyById.getDate());
		assertEquals("Time is not correct", LocalTime.of(9, 15), supplyById.getTime());
		assertEquals("Observation is not correct", "None", supplyById.getObservation());
		assertEquals("Pathology is not correct", "Broken leg", supplyById.getPathology());
		
		supplyDAO.delete(supplyById);
		
		assertNull("The supply still exist", supplyDAO.findById(consecutive));
		
		consecutive = new Long(4);
		
		supplyById = supplyDAO.findById(consecutive);
		
		assertNotNull(supplyById);
		
		assertEquals("Medicine is not correct", medicine, supplyById.getMedicine());
		assertEquals("Amount is not correct", new Integer(10), supplyById.getAmount());
		assertEquals("Patient is not correct", patient, supplyById.getPatient());
		assertEquals("Date is not correct", LocalDate.of(2019, 6, 16), supplyById.getDate());
		assertEquals("Time is not correct", LocalTime.of(10, 15), supplyById.getTime());
		assertEquals("Observation is not correct", "None", supplyById.getObservation());
		assertEquals("Pathology is not correct", "Broken arm", supplyById.getPathology());
		
		supplyDAO.delete(supplyById);
		
		assertNull("The medicine still exist", supplyDAO.findById(consecutive));
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test06FindBetweenAmounts() {
		
		assertNotNull(supplyDAO);
		
		setUpDAO();
		
		List<Supply> suppliesByAmount = supplyDAO.findBetweenAmounts(new Integer(5), new Integer(10));
		
		assertNotNull(suppliesByAmount);
		
		assertEquals("DAO is suppose to have two elements", 2, suppliesByAmount.size());
		
		suppliesByAmount = supplyDAO.findBetweenAmounts(new Integer(15), new Integer(20));
		
		assertNotNull(suppliesByAmount);
		
		assertEquals("DAO is not suppose to have elements", 0, suppliesByAmount.size());
		
		suppliesByAmount = supplyDAO.findBetweenAmounts(new Integer(0), new Integer(3));
		
		assertNotNull(suppliesByAmount);
		
		assertEquals("DAO is suppose to have one elements", 1, suppliesByAmount.size());
		
	}
	
}
