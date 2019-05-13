package com.proyectDAO.emergencyCare.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class Medicine {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long consecutive;
	
	@NonNull
	private String name;
	
	@NonNull
	private String genericName;
	
	@NonNull
	private String laboratory;
	
	@NonNull
	private String typeOfAdministration;
	
	private String indicAndContraindic;
	
	@OneToMany
	@ToString.Exclude
	private List<MedicineInventory> medicineInventory;
	
}
