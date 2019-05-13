package com.proyectDAO.emergencyCare.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class EmergencyAttention {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long consecutive;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	
	@DateTimeFormat(pattern="HH:mm")
	private LocalTime time;
	
	@ManyToOne
	private Patient patient;
	
	@NonNull
	private String generalDescription;
	
	@NonNull
	private String procedurePerformed;
	
	@NonNull
	private Boolean forwarded;
	
	private String forwardedPlace;
	
	@NonNull
	private String observations;
	
	@OneToMany
	private List<Supply> medicinesSupplied;
	
}
