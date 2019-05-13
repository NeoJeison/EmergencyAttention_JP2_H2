package com.proyectDAO.emergencyCare.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class MedicineInventory {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long consecutive;
	
	@ManyToOne
	private Medicine medicine;
	
	@NonNull
	private Integer quantityAvailable;
	
	@NonNull
	private String location;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate expirationDate;
	
}
