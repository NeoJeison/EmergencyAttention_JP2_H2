package com.proyectDAO.emergencyCare.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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

import com.proyectDAO.emergencyCare.dao.IEmergencyAttentionDAO;
import com.proyectDAO.emergencyCare.dao.IPatientDAO;
import com.proyectDAO.emergencyCare.model.EmergencyAttention;
import com.proyectDAO.emergencyCare.model.Patient;
import com.proyectDAO.emergencyCare.model.State;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Rollback(false)
public class TestEmergencyAttentionDAO {
	
	@Autowired
	private IEmergencyAttentionDAO emergencyAttentionDAO;
	
	@Autowired
	private IPatientDAO patientDAO;
	
	private Patient patient;
	
	private Patient patient2;
		
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void setUpData() {		
		patient = new Patient();
		patient.setDocument("0001");
		patient.setName("Jeison");
		patient.setLastNames("Mejia");
		patient.setState(State.Active);
		patient.setAcademicProgram("Software engineering");
		patient.setAcademicDependence("None");
		patientDAO.save(patient);
		
		patient2 = new Patient();
		patient2.setDocument("0000");
		patient2.setName("Juan");
		patient2.setLastNames("Perez");
		patient2.setState(State.Inactive);
		patient2.setAcademicProgram("Software engineering");
		patient2.setAcademicDependence("None");
		patientDAO.save(patient2);
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test01Save() {
		
		setUpData();
		
		assertNotNull(emergencyAttentionDAO);
		
		Long consecutive = new Long(1);
		
		EmergencyAttention emergencyAttention = new EmergencyAttention();
		emergencyAttention.setDate(LocalDate.of(2019, 06, 15));
		emergencyAttention.setTime(LocalTime.of(9, 15));
		emergencyAttention.setPatient(patient);
		emergencyAttention.setGeneralDescription("Patient arrived with discomfort");
		emergencyAttention.setProcedurePerformed("Patient didn't need any medicine");
		emergencyAttention.setForwarded(new Boolean(false));
		emergencyAttention.setForwardedPlace("None");
		emergencyAttention.setObservations("None");
		
		emergencyAttentionDAO.save(emergencyAttention);
		
		assertEquals(emergencyAttention, emergencyAttentionDAO.findById(consecutive));
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test02Update() {
		
		assertNotNull(emergencyAttentionDAO);
		
		Long consecutive = new Long(1);
		
		EmergencyAttention emergencyAttention = emergencyAttentionDAO.findById(consecutive);
		
		assertNotNull(emergencyAttention);
		
		LocalDate ld = LocalDate.of(2019, 5, 16);
		String procedure = "None";
		emergencyAttention.setDate(ld);
		emergencyAttention.setProcedurePerformed(procedure);
		
		emergencyAttentionDAO.update(emergencyAttention);
		
		assertEquals(ld, emergencyAttentionDAO.findById(consecutive).getDate());
		assertEquals(procedure, emergencyAttentionDAO.findById(consecutive).getProcedurePerformed());
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test03Delete() {
		
		assertNotNull(emergencyAttentionDAO);
		
		Long consecutive = new Long(1);
		
		EmergencyAttention emergencyAttention = emergencyAttentionDAO.findById(consecutive);
		
		assertNotNull(emergencyAttention);
		
		emergencyAttentionDAO.delete(emergencyAttention);
		
		assertNull("The attention was not deleted", emergencyAttentionDAO.findById(consecutive));
		
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void setUpDAO() {
		
		patient = patientDAO.findByDocument("0001");
		patient2 = patientDAO.findByDocument("0000");
				
		EmergencyAttention emergencyAttention1 = new EmergencyAttention();
		emergencyAttention1.setDate(LocalDate.of(2019, 06, 15));
		emergencyAttention1.setTime(LocalTime.of(9, 15));
		emergencyAttention1.setPatient(patient2);
		emergencyAttention1.setGeneralDescription("Patient arrived with discomfort");
		emergencyAttention1.setProcedurePerformed("Patient didn't need any medicine");
		emergencyAttention1.setForwarded(new Boolean(false));
		emergencyAttention1.setForwardedPlace("None");
		emergencyAttention1.setObservations("None");
		
		emergencyAttentionDAO.save(emergencyAttention1);
		
		EmergencyAttention emergencyAttention2 = new EmergencyAttention();
		emergencyAttention2.setDate(LocalDate.of(2019, 06, 17));
		emergencyAttention2.setTime(LocalTime.of(10, 15));
		emergencyAttention2.setPatient(patient);
		emergencyAttention2.setGeneralDescription("Patient arrived with a strong headache");
		emergencyAttention2.setProcedurePerformed("Patient needed some tests");
		emergencyAttention2.setForwarded(new Boolean(true));
		emergencyAttention2.setForwardedPlace("University hospital");
		emergencyAttention2.setObservations("None");
		
		emergencyAttentionDAO.save(emergencyAttention2);
		
		EmergencyAttention emergencyAttention3 = new EmergencyAttention();
		emergencyAttention3.setDate(LocalDate.of(2019, 06, 19));
		emergencyAttention3.setTime(LocalTime.of(11, 15));
		emergencyAttention3.setPatient(patient);
		emergencyAttention3.setGeneralDescription("Patient arrived with a mild migraine");
		emergencyAttention3.setProcedurePerformed("Patient needed rest");
		emergencyAttention3.setForwarded(new Boolean(false));
		emergencyAttention3.setForwardedPlace("None");
		emergencyAttention3.setObservations("None");
		
		emergencyAttentionDAO.save(emergencyAttention3);
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test04FindAll() {
		
		setUpDAO();
		
		assertNotNull(emergencyAttentionDAO);
		
		List<EmergencyAttention> listOfAttentions = emergencyAttentionDAO.findAll();
		
		assertNotNull(listOfAttentions);
		
		assertEquals("DAO is suppose to have three elements", 3, listOfAttentions.size());
		
		Long consecutive = new Long(2);
		
		EmergencyAttention emergencyAttention = emergencyAttentionDAO.findById(consecutive);
		
		emergencyAttentionDAO.delete(emergencyAttention);
		
		assertNull("The attention was not deleted", emergencyAttentionDAO.findById(consecutive));
		
		listOfAttentions = emergencyAttentionDAO.findAll();
		
		assertEquals("DAO is suppose to have two elements", 2, listOfAttentions.size());
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test05FindByConsecutive() {
		
		assertNotNull(emergencyAttentionDAO);
		
		Long consecutive = new Long(3);
		
		EmergencyAttention attentionById = emergencyAttentionDAO.findById(consecutive);
		
		assertNotNull(attentionById);
		
		assertEquals("Date is not correct", LocalDate.of(2019, 6, 17), attentionById.getDate());
		assertEquals("Time is not correct", LocalTime.of(10, 15), attentionById.getTime());
		patient = patientDAO.findByDocument("0001");
		assertEquals("Patient is not correct", patient, attentionById.getPatient());
		assertEquals("General description is not correct", "Patient arrived with a strong headache", attentionById.getGeneralDescription());
		assertEquals("Procedure performed is not correct", "Patient needed some tests", attentionById.getProcedurePerformed());
		assertEquals("Forwarded is not correct", new Boolean(true), attentionById.getForwarded());
		assertEquals("Forwarded place is not correct", "University hospital", attentionById.getForwardedPlace());
		assertEquals("Observations are not correct", "None", attentionById.getObservations());
		
		emergencyAttentionDAO.delete(attentionById);
		
		assertNull("The attention still exist", emergencyAttentionDAO.findById(consecutive));
		
		consecutive = new Long(4);
		
		attentionById = emergencyAttentionDAO.findById(consecutive);
		
		assertNotNull(attentionById);
		
		assertEquals("Date is not correct", LocalDate.of(2019, 6, 19), attentionById.getDate());
		assertEquals("Time is not correct", LocalTime.of(11, 15), attentionById.getTime());
		assertEquals("Patient is not correct", patient, attentionById.getPatient());
		assertEquals("General description is not correct", "Patient arrived with a mild migraine", attentionById.getGeneralDescription());
		assertEquals("Procedure performed is not correct", "Patient needed rest", attentionById.getProcedurePerformed());
		assertEquals("Forwarded is not correct", new Boolean(false), attentionById.getForwarded());
		assertEquals("Forwarded place is not correct", "None", attentionById.getForwardedPlace());
		assertEquals("Observations are not correct", "None", attentionById.getObservations());
		
		emergencyAttentionDAO.delete(attentionById);
		
		assertNull("The attention still exist", emergencyAttentionDAO.findById(consecutive));
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test06FindBetweenDates() {
		
		assertNotNull(emergencyAttentionDAO);
		
		setUpDAO();
		
		List<EmergencyAttention> attentionsByDate = emergencyAttentionDAO.findBetweenDates(LocalDate.of(2019, 06, 16), LocalDate.of(2019, 06, 21));
		
		assertNotNull(attentionsByDate);
		
		assertEquals("DAO is suppose to have two elements", 2, attentionsByDate.size());
		
		attentionsByDate = emergencyAttentionDAO.findBetweenDates(LocalDate.of(2019, 04, 15), LocalDate.of(2019, 05, 15));
		
		assertNotNull(attentionsByDate);
		
		assertEquals("DAO is not suppose to have elements", 0, attentionsByDate.size());
		
		attentionsByDate = emergencyAttentionDAO.findBetweenDates(LocalDate.of(2019, 06, 15), LocalDate.of(2019, 06, 15));
		
		assertNotNull(attentionsByDate);
		
		assertEquals("DAO is suppose to have one elements", 1, attentionsByDate.size());
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test07findPatientsCountAttentionsOrderedByDocument() {
		
		List<Object> queryResult = emergencyAttentionDAO.findPatientsCountAttentionsOrderedByDocument();
						
		List<Patient> patients = new ArrayList<Patient>();
		List<Long> attentionsOfPatients = new ArrayList<Long>();
		
		for(int i = 0; i < queryResult.size(); i++) {
			Object[] data = (Object[])queryResult.get(i);
			for(Object objeto : data) {
				if(objeto instanceof Patient) {
					patients.add((Patient)objeto);
				}else {
					attentionsOfPatients.add((Long)objeto);
				}
			}			
		}
		
		assertEquals("Patient is not the one set for the attentions", patientDAO.findByDocument("0000"), patients.get(0));
		assertEquals("Patient should have one attentions", new Long(1), attentionsOfPatients.get(0));
		
		assertEquals("Patient is not the one set for the attentions", patientDAO.findByDocument("0001"), patients.get(1));
		assertEquals("Patient should have two attentions", new Long(2), attentionsOfPatients.get(1));
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test08findPatientsMoreThanTwoAttentionLastMonth() {
		
		patient = patientDAO.findByDocument("0001");
		
		EmergencyAttention emergencyAttention1 = new EmergencyAttention();
		emergencyAttention1.setDate(LocalDate.of(2019, 05, 01));
		emergencyAttention1.setTime(LocalTime.of(9, 15));
		emergencyAttention1.setPatient(patient);
		emergencyAttention1.setGeneralDescription("Patient arrived with discomfort");
		emergencyAttention1.setProcedurePerformed("Patient didn't need any medicine");
		emergencyAttention1.setForwarded(new Boolean(false));
		emergencyAttention1.setForwardedPlace("None");
		emergencyAttention1.setObservations("None");
		
		emergencyAttentionDAO.save(emergencyAttention1);
		
		List<Patient> patients = emergencyAttentionDAO.findPatientsMoreThanTwoAttentionLastMonth();
		
		assertEquals("There should not be patients who meet the criteria", 0, patients.size());
		
		List<EmergencyAttention> attentions = emergencyAttentionDAO.findAll();
		
		LocalDate date = LocalDate.now().minusDays(10);
		attentions.get(0).setDate(date);
		
		date = LocalDate.now().minusDays(5);
		attentions.get(1).setDate(date);
		
		date = LocalDate.now().minusDays(20);
		attentions.get(2).setDate(date);
		
		emergencyAttentionDAO.update(attentions.get(0));
		emergencyAttentionDAO.update(attentions.get(1));
		emergencyAttentionDAO.update(attentions.get(2));
		
		patient = patientDAO.findByDocument("0001");
		
		patients = emergencyAttentionDAO.findPatientsMoreThanTwoAttentionLastMonth();
		
		assertEquals("There should be only one patient who meet the criteria", 1, patients.size());
		assertEquals("Patient who meet the criteria is not correct", patient, patients.get(0));
		
	}
	
	
}
