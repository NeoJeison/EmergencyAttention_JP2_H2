package com.proyectDAO.emergencyCare.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
import com.proyectDAO.emergencyCare.dao.IMedicineInventoryDAO;
import com.proyectDAO.emergencyCare.model.Medicine;
import com.proyectDAO.emergencyCare.model.MedicineInventory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Rollback(false)
public class TestMedicineInventoryDAO {
	
	@Autowired
	private IMedicineInventoryDAO medicineInventoryDAO;
	
	@Autowired
	private IMedicineDAO medicineDAO;
	
	private Medicine medicine;
	
	private Medicine medicine2;
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void setUpMedicine() {
		medicine = new Medicine();
		medicine.setName("Acetaminofen");
		medicine.setGenericName("Acetaminofen");
		medicine.setLaboratory("JGB");
		medicine.setTypeOfAdministration("Oral");
		medicine.setIndicAndContraindic("None");
		medicineDAO.save(medicine);
		
		medicine2 = new Medicine();
		medicine2.setName("Naproxeno");
		medicine2.setGenericName("Naproxeno");
		medicine2.setLaboratory("Genfar");
		medicine2.setTypeOfAdministration("Oral");
		medicine2.setIndicAndContraindic("None");
		medicineDAO.save(medicine2);
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test01Save() {
		
		setUpMedicine();
		
		assertNotNull(medicineInventoryDAO);
		
		Long consecutive = new Long(3);
		LocalDate ld = LocalDate.of(2020, 2, 15);
		
		MedicineInventory medicineInventory = new MedicineInventory();
		medicineInventory.setQuantityAvailable(5);
		medicineInventory.setLocation("Vitrina");
		medicineInventory.setMedicine(medicine);
		medicineInventory.setExpirationDate(ld);
		
		medicineInventoryDAO.save(medicineInventory);
		
		assertEquals(medicineInventory, medicineInventoryDAO.findById(consecutive));
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test02Update() {
		
		assertNotNull(medicineInventoryDAO);
		
		Long consecutive = new Long(3);
		
		MedicineInventory medicineInventory = medicineInventoryDAO.findById(consecutive);
		
		assertNotNull(medicineInventory);
		
		String newLocation = "Bodega principal";
		Integer newQuantityAvailable = 8;
		medicineInventory.setLocation(newLocation);
		medicineInventory.setQuantityAvailable(newQuantityAvailable);
		
		medicineInventoryDAO.update(medicineInventory);
		
		assertEquals(newLocation, medicineInventoryDAO.findById(consecutive).getLocation());
		assertEquals(newQuantityAvailable, medicineInventoryDAO.findById(consecutive).getQuantityAvailable());
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test03Delete() {
		
		assertNotNull(medicineInventoryDAO);
		
		Long consecutive = new Long(3);
		
		MedicineInventory medicineInventory = medicineInventoryDAO.findById(consecutive);
		
		assertNotNull(medicineInventory);
		
		medicineInventoryDAO.delete(medicineInventory);
		
		assertNull("The medicine inventory was not deleted", medicineInventoryDAO.findById(consecutive));
		
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void setUpDAO() {
		
		medicine = medicineDAO.findById(new Long(1));
		medicine2 = medicineDAO.findById(new Long(2));
		
		MedicineInventory medicineInventory1 = new MedicineInventory();
		medicineInventory1.setQuantityAvailable(5);
		medicineInventory1.setLocation("Vitrina");
		medicineInventory1.setMedicine(medicine2);
		medicineInventory1.setExpirationDate(LocalDate.of(2020, 2, 15));
		
		medicineInventoryDAO.save(medicineInventory1);
		
		MedicineInventory medicineInventory2 = new MedicineInventory();
		medicineInventory2.setQuantityAvailable(20);
		medicineInventory2.setLocation("Bodega trasera");
		medicineInventory2.setMedicine(medicine);
		medicineInventory2.setExpirationDate(LocalDate.of(2019, 10, 15));
		
		medicineInventoryDAO.save(medicineInventory2);
		
		MedicineInventory medicineInventory3 = new MedicineInventory();
		medicineInventory3.setQuantityAvailable(23);
		medicineInventory3.setLocation("Bodega secundaria");
		medicineInventory3.setMedicine(medicine);
		medicineInventory3.setExpirationDate(LocalDate.of(2021, 5, 20));
		
		medicineInventoryDAO.save(medicineInventory3);
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test04FindAll() {
		
		setUpDAO();
		
		assertNotNull(medicineInventoryDAO);
		
		List<MedicineInventory> listOfMedicineInvent = medicineInventoryDAO.findAll();
		
		assertNotNull(listOfMedicineInvent);
		
		assertEquals("DAO is suppose to have three elements", 3, listOfMedicineInvent.size());
		
		Long consecutive = new Long(6);
		
		MedicineInventory medicineInventory = medicineInventoryDAO.findById(consecutive);
		
		medicineInventoryDAO.delete(medicineInventory);
		
		assertNull("The medicine inventory was not deleted", medicineInventoryDAO.findById(consecutive));
		
		listOfMedicineInvent = medicineInventoryDAO.findAll();
		
		assertEquals("DAO is suppose to have two elements", 2, listOfMedicineInvent.size());
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test05FindByConsecutive() {
		
		assertNotNull(medicineInventoryDAO);
		
		medicine = medicineDAO.findById(new Long(1));
		medicine2 = medicineDAO.findById(new Long(2));
		
		Long consecutive = new Long(4);
				
		MedicineInventory medicineInventById = medicineInventoryDAO.findById(consecutive);
		
		assertNotNull(medicineInventById);
		
		assertEquals("Quantity available is not correct", new Integer(5), medicineInventById.getQuantityAvailable());
		assertEquals("Location not correct", "Vitrina", medicineInventById.getLocation());
		assertEquals("Medicine not correct", medicine2, medicineInventById.getMedicine());
		assertEquals("Indications are not correct", LocalDate.of(2020, 2, 15), medicineInventById.getExpirationDate());
		
		medicineInventoryDAO.delete(medicineInventById);
		
		assertNull("The medicine still exist", medicineInventoryDAO.findById(consecutive));
		
		consecutive = new Long(5);
		
		medicineInventById = medicineInventoryDAO.findById(consecutive);
		
		assertNotNull(medicineInventById);
		
		assertEquals("Quantity available is not correct", new Integer(20), medicineInventById.getQuantityAvailable());
		assertEquals("Location not correct", "Bodega trasera", medicineInventById.getLocation());
		assertEquals("Medicine not correct", medicine, medicineInventById.getMedicine());
		assertEquals("Indications are not correct", LocalDate.of(2019, 10, 15), medicineInventById.getExpirationDate());
		
		medicineInventoryDAO.delete(medicineInventById);
		
		assertNull("The medicine still exist", medicineInventoryDAO.findById(consecutive));
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void test06FindMedsWithInventoryLessThan10() {
		
		setUpDAO();
				
		assertNotNull(medicineInventoryDAO);
		
		medicine = medicineDAO.findById(new Long(1));
		medicine2 = medicineDAO.findById(new Long(2));
		
		List<Medicine> medicines = medicineInventoryDAO.findMedsWithInventoryLessThan10();
				
		assertEquals("The amount of medicines with inventory less than 10 should be 1", 1, medicines.size());
		assertEquals("The medicine with less than 10 medicine inventory is not correct", medicine2, medicines.get(0));
		
		MedicineInventory medicineInventory = medicineInventoryDAO.findById(new Long(8));
		medicineInventoryDAO.delete(medicineInventory);
		
		medicineInventory = medicineInventoryDAO.findById(new Long(9));
		medicineInventory.setQuantityAvailable(3);
		
		medicineInventoryDAO.update(medicineInventory);
		
		medicines = medicineInventoryDAO.findMedsWithInventoryLessThan10();
		
		assertEquals("The amount of medicines with inventory less than 10 should be 2", 2, medicines.size());
		
		List<Medicine> medicinesKnown = new ArrayList<Medicine>();
		medicinesKnown.add(medicine);
		medicinesKnown.add(medicine2);
		
		assertEquals(medicinesKnown, medicines);
		
	}
	
}
