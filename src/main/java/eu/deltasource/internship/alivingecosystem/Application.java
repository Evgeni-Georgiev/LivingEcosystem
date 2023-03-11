package eu.deltasource.internship.alivingecosystem;

import eu.deltasource.internship.alivingecosystem.service.ecoSystemService.EcoSystemService;
import eu.deltasource.internship.alivingecosystem.service.ecoSystemService.EcoSystemServiceImpl;

public class Application {

	public static void main(String[] args) {

		EcoSystemService ecoSystemService = new EcoSystemServiceImpl();
		try {
			ecoSystemService.ecoSystem();
		} catch(InterruptedException e) {
			e.getMessage();
		}

	}
}
