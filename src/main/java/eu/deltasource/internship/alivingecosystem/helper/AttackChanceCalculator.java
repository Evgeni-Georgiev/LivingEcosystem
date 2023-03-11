package eu.deltasource.internship.alivingecosystem.helper;

import eu.deltasource.internship.alivingecosystem.enums.CalculationType;
import eu.deltasource.internship.alivingecosystem.enums.LivingType;
import eu.deltasource.internship.alivingecosystem.model.animals.Carnivore;
import eu.deltasource.internship.alivingecosystem.model.animals.Herbivore;

import static eu.deltasource.internship.alivingecosystem.helper.ScaleCalculator.attackPointsScale;
import static eu.deltasource.internship.alivingecosystem.helper.ScaleCalculator.escapePointsScale;

public class AttackChanceCalculator {

	public static double chanceForSuccessOfAttack(Carnivore carnivore, Herbivore herbivore) {

		double chanceOfSuccess = calculateChanceOfSuccess(carnivore, herbivore);

		if (carnivore.getLivingType() == LivingType.ALONE) {
			chanceOfSuccess = applyAloneCarnivoreModifier(chanceOfSuccess);
		}

		if (herbivore.getLivingType() == LivingType.GROUP) {
			chanceOfSuccess = applyGroupHerbivoreModifier(chanceOfSuccess);
		}

		if (carnivore.getWeight() < herbivore.getWeight() && carnivore.getLivingType() == LivingType.ALONE) {
			chanceOfSuccess = applyWeightDifferenceModifier(chanceOfSuccess, carnivore, herbivore);
		}

		return chanceOfSuccess;
	}

	public static double calculateChanceOfSuccess(Carnivore carnivore, Herbivore herbivore) {
		double attackPointsScale = attackPointsScale(carnivore);
		double escapePointsScale = escapePointsScale(herbivore);
		return attackPointsScale / (attackPointsScale + escapePointsScale) * 100;
	}

	public static double applyAloneCarnivoreModifier(double chanceOfSuccess) {
		return chanceOfSuccess * CalculationType.LESS_CHANCE_OF_SUCCESS_WHEN_ATTACK_ALONE.getValue(); // decrease by 50%
	}

	public static double applyGroupHerbivoreModifier(double chanceOfSuccess) {
		return chanceOfSuccess * CalculationType.LESS_CHANCE_OF_SUCCESS_WHEN_CARNIVORES_ATTACK.getValue(); // increase by 30%
	}

	public static double applyWeightDifferenceModifier(double chanceOfSuccess, Carnivore carnivore, Herbivore herbivore) {
		double reductionRation = (double) herbivore.getWeight() / (double) carnivore.getWeight();
		return chanceOfSuccess / reductionRation;
	}
}
