package com.proyectDAO.emergencyCare.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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

import com.proyectDAO.emergencyCare.dao.IPatientDAO;
import com.proyectDAO.emergencyCare.model.Patient;
import com.proyectDAO.emergencyCare.model.State;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Rollback(false)
public class TestPatientDAO {
	
	@Autowired
	private IPatientDAO patientDAO;
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test01Save() {
		
		assertNotNull(patientDAO);
		
		Patient patient = new Patient();
		patient.setDocument("123456");
		patient.setName("Jeison");
		patient.setLastNames("Mejia");
		patient.setState(State.Active);
		patient.setAcademicProgram("Software engineering");
		patient.setAcademicDependence("None");
		
		patientDAO.save(patient);
		
		assertEquals(patient, patientDAO.findByDocument(patient.getDocument()));
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test02Update() {
		
		assertNotNull(patientDAO);
		
		Patient patient = patientDAO.findByDocument("123456");
		
		assertNotNull(patient);
		
		String newLastName = "Perez Rodriguez";
		patient.setLastNames(newLastName);
		
		patientDAO.update(patient);
		
		assertEquals(newLastName, patientDAO.findByDocument(patient.getDocument()).getLastNames());
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test03Delete() {
		
		assertNotNull(patientDAO);
		
		Patient patient = patientDAO.findByDocument("123456");
		
		assertNotNull(patient);
		
		patientDAO.delete(patient);
		
		assertNull("The patient was not deleted", patientDAO.findByDocument(patient.getDocument()));
		
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void setUpDAO() {
		
		Patient patient1 = new Patient();
		patient1.setDocument("000001");
		patient1.setName("Jeison");
		patient1.setLastNames("Mejia");
		patient1.setState(State.Active);
		patient1.setAcademicProgram("Software engineering");
		patient1.setAcademicDependence("None");
		
		patientDAO.save(patient1);
		
		Patient patient2 = new Patient();
		patient2.setDocument("000002");
		patient2.setName("Juan");
		patient2.setLastNames("Rodriguez");
		patient2.setState(State.Inactive);
		patient2.setAcademicProgram("Laws");
		patient2.setAcademicDependence("None");
		
		patientDAO.save(patient2);
		
		Patient patient3 = new Patient();
		patient3.setDocument("000003");
		patient3.setName("Juan");
		patient3.setLastNames("Perez");
		patient3.setState(State.Inactive);
		patient3.setAcademicProgram("Telematics engineering");
		patient3.setAcademicDependence("None");
		
		patientDAO.save(patient3);
		
		Patient patient4 = new Patient();
		patient4.setDocument("000004");
		patient4.setName("Andrea");
		patient4.setLastNames("Perez");
		patient4.setState(State.Active);
		patient4.setAcademicProgram("Software engineering");
		patient4.setAcademicDependence("None");
		
		patientDAO.save(patient4);
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test04FindAll() {
		
		setUpDAO();
		
		assertNotNull(patientDAO);
		
		List<Patient> listOfPatients = patientDAO.findAll();
		
		assertNotNull(listOfPatients);
		
		assertEquals("DAO is suppose to have four elements", 4, listOfPatients.size());
		
		Patient patient = patientDAO.findByDocument("000001");
		
		patientDAO.delete(patient);
		
		assertNull("The patient was not deleted", patientDAO.findByDocument(patient.getDocument()));
		
		listOfPatients = patientDAO.findAll();
		
		assertEquals("DAO is suppose to have three elements", 3, listOfPatients.size());
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test05FindByName() {
		
		assertNotNull(patientDAO);
		
		List<Patient> patientsByName = patientDAO.findByName("Juan");
		
		assertNotNull(patientsByName);
		
		assertEquals("DAO is suppose to have two elements", 2, patientsByName.size());
		
		Patient patient = patientDAO.findByDocument(patientsByName.get(0).getDocument());
		
		patientDAO.delete(patient);
		
		assertNull("The patient was not deleted", patientDAO.findByDocument(patient.getDocument()));
		
		patientsByName = patientDAO.findByName("Juan");
		
		assertEquals("DAO is suppose to have one elements", 1, patientsByName.size());
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test06FindByLastNames() {
		
		assertNotNull(patientDAO);
		
		List<Patient> patientsByLastNames = patientDAO.findByLastNames("Perez");
		
		assertNotNull(patientsByLastNames);
		
		assertEquals("DAO is suppose to have two elements", 2, patientsByLastNames.size());
		
		Patient patient = patientDAO.findByDocument(patientsByLastNames.get(0).getDocument());
		
		patientDAO.delete(patient);
		
		assertNull("The patient was not deleted", patientDAO.findByDocument(patient.getDocument()));
		
		patientsByLastNames = patientDAO.findByLastNames("Perez");
		
		assertEquals("DAO is suppose to have one elements", 1, patientsByLastNames.size());
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test07FindByDocument() {
		
		assertNotNull(patientDAO);
		
		Patient patientByDocument = patientDAO.findByDocument("000004");
		
		assertNotNull(patientByDocument);
		
		assertEquals("Name is not correct", "Andrea", patientByDocument.getName());
		assertEquals("Last names are not correct", "Perez", patientByDocument.getLastNames());
		assertEquals("State is not correct", State.Active, patientByDocument.getState());
		assertEquals("Academic program is not correct", "Software engineering", patientByDocument.getAcademicProgram());
		assertEquals("Academic dependence is not correct", "None", patientByDocument.getAcademicDependence());
		
		patientDAO.delete(patientByDocument);
		
		assertNull("The patient still exist", patientDAO.findByDocument(patientByDocument.getDocument()));
		
	}
	
}
