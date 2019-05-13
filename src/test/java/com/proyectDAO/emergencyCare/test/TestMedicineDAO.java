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

import com.proyectDAO.emergencyCare.dao.IMedicineDAO;
import com.proyectDAO.emergencyCare.model.Medicine;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Rollback(false)
public class TestMedicineDAO {
	
	@Autowired
	private IMedicineDAO medicineDAO;
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test01Save() {
		
		assertNotNull(medicineDAO);
		
		Long consecutive = new Long(1);
		
		Medicine medicine = new Medicine();
		medicine.setName("Acetaminofen");
		medicine.setGenericName("Acetaminofen");
		medicine.setLaboratory("JGB");
		medicine.setTypeOfAdministration("Oral");
		medicine.setIndicAndContraindic("None");
		
		medicineDAO.save(medicine);
		
		assertEquals(medicine, medicineDAO.findById(consecutive));
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test02Update() {
		
		assertNotNull(medicineDAO);
		
		Long consecutive = new Long(1);
		
		Medicine medicine = medicineDAO.findById(consecutive);
		
		assertNotNull(medicine);
		
		String newLaboratory = "Genfar";
		String newTypeOfAdmin = "Intramuscular";
		medicine.setLaboratory(newLaboratory);
		medicine.setTypeOfAdministration(newTypeOfAdmin);
		
		medicineDAO.update(medicine);
		
		assertEquals(newLaboratory, medicineDAO.findById(consecutive).getLaboratory());
		assertEquals(newTypeOfAdmin, medicineDAO.findById(consecutive).getTypeOfAdministration());
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test03Delete() {
		
		assertNotNull(medicineDAO);
		
		Long consecutive = new Long(1);
		
		Medicine medicine = medicineDAO.findById(consecutive);
		
		assertNotNull(medicine);
		
		medicineDAO.delete(medicine);
		
		assertNull("The medicine was not deleted", medicineDAO.findById(consecutive));
		
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void setUpDAO() {
		
		Medicine medicine1 = new Medicine();
		medicine1.setName("Acetaminofen");
		medicine1.setGenericName("Acetaminofen");
		medicine1.setLaboratory("JGB");
		medicine1.setTypeOfAdministration("Oral");
		medicine1.setIndicAndContraindic("None");
		
		medicineDAO.save(medicine1);
		
		Medicine medicine2 = new Medicine();
		medicine2.setName("Diclofenaco");
		medicine2.setGenericName("Diclofenaco");
		medicine2.setLaboratory("Genfar");
		medicine2.setTypeOfAdministration("Intramuscular");
		medicine2.setIndicAndContraindic("None");
		
		
		medicineDAO.save(medicine2);
		
		Medicine medicine3 = new Medicine();
		medicine3.setName("Naproxeno");
		medicine3.setGenericName("Naproxeno");
		medicine3.setLaboratory("Genfar");
		medicine3.setTypeOfAdministration("Oral");
		medicine3.setIndicAndContraindic("None");
		
		medicineDAO.save(medicine3);
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test04FindAll() {
		
		setUpDAO();
		
		assertNotNull(medicineDAO);
		
		List<Medicine> listOfMedicines = medicineDAO.findAll();
		
		assertNotNull(listOfMedicines);
		
		assertEquals("DAO is suppose to have three elements", 3, listOfMedicines.size());
		
		Long consecutive = new Long(3);
		
		Medicine medicine = medicineDAO.findById(consecutive);
		
		medicineDAO.delete(medicine);
		
		assertNull("The medicine was not deleted", medicineDAO.findById(consecutive));
		
		listOfMedicines = medicineDAO.findAll();
		
		assertEquals("DAO is suppose to have two elements", 2, listOfMedicines.size());
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test05FindByConsecutive() {
		
		assertNotNull(medicineDAO);
		
		Long consecutive = new Long(2);
		
		Medicine medicineById = medicineDAO.findById(consecutive);
		
		assertNotNull(medicineById);
		
		assertEquals("Name is not correct", "Acetaminofen", medicineById.getName());
		assertEquals("Generic name are not correct", "Acetaminofen", medicineById.getGenericName());
		assertEquals("Laboratory is not correct", "JGB", medicineById.getLaboratory());
		assertEquals("Type of administration is not correct", "Oral", medicineById.getTypeOfAdministration());
		assertEquals("Indications are not correct", "None", medicineById.getIndicAndContraindic());
		
		medicineDAO.delete(medicineById);
		
		assertNull("The medicine still exist", medicineDAO.findById(consecutive));
		
		consecutive = new Long(4);
		
		medicineById = medicineDAO.findById(consecutive);
		
		assertNotNull(medicineById);
		
		assertEquals("Name is not correct", "Naproxeno", medicineById.getName());
		assertEquals("Generic name are not correct", "Naproxeno", medicineById.getGenericName());
		assertEquals("Laboratory is not correct", "Genfar", medicineById.getLaboratory());
		assertEquals("Type of administration is not correct", "Oral", medicineById.getTypeOfAdministration());
		assertEquals("Indications are not correct", "None", medicineById.getIndicAndContraindic());
		
		medicineDAO.delete(medicineById);
		
		assertNull("The medicine still exist", medicineDAO.findById(consecutive));
		
	}
	
}
