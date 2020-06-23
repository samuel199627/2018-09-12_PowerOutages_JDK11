package it.polito.tdp.poweroutages.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Model m = new Model();
		
		System.out.println(""+ChronoUnit.MONTHS.between(LocalDate.of(2020, 02, 22), LocalDate.of(2020, 03, 21)));
		
		
	}

}
