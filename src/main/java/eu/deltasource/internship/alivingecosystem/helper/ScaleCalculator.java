package eu.deltasource.internship.alivingecosystem.helper;

import eu.deltasource.internship.alivingecosystem.model.animals.Carnivore;
import eu.deltasource.internship.alivingecosystem.model.animals.Herbivore;

public class ScaleCalculator {

	public static double attackPointsScale(Carnivore carnivore) {
		var scaleFormula = calculateScaleFormula(carnivore.getAge(), carnivore.getMaxAge());
		return carnivore.getAttackPoints() - scaleFormula;
	}

	public static double escapePointsScale(Herbivore herbivore) {
		var scaleFormula = calculateScaleFormula(herbivore.getAge(), herbivore.getMaxAge());
		return herbivore.getEscapePoints() - scaleFormula;
	}

	public static double calculateScaleFormula(int currentAge, int maxAge) {
		return 1 - (double) currentAge / (double) maxAge;
	}
}
