package eu.deltasource.internship.alivingecosystem.helper;

import eu.deltasource.internship.alivingecosystem.model.animals.Carnivore;

public class DecreaseHungerLevelCalculator {

	public static int decreaseHungerLevelByFormula(int caughtPreyWeight, int attackerWeight) {
		return (caughtPreyWeight * 100) / attackerWeight;
	}

	public static void validateIfHungerLevelIsLessThanZero(Carnivore carnivore) {
		if (carnivore.getHungerLevel() <= 0) {
			carnivore.setHungerLevel(0);
		}
	}
}
