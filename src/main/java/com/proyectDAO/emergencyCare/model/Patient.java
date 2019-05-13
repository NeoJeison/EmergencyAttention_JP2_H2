package com.proyectDAO.emergencyCare.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class Patient {
	
	@Id
	@NonNull
	private String document;
	
	@NonNull
	private String name;
	
	@NonNull
	private String lastNames;
	
	private String academicProgram;
	
	private String academicDependence;
	
	@Enumerated(EnumType.STRING)
	private State state;
	
	
	
}
