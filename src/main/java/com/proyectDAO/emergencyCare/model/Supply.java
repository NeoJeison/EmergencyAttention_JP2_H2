package com.proyectDAO.emergencyCare.model;

import java.time.LocalDate;
import java.time.LocalTime;

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
public class Supply {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long consecutive;
	
	@ManyToOne
	private Medicine medicine;
	
	private Integer amount;
	
	@ManyToOne
	private Patient patient;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	
	@DateTimeFormat(pattern="HH:mm")
	private LocalTime time;
	
	private String observation;
	
	@NonNull
	private String pathology;
	
}
