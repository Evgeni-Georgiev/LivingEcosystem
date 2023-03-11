package eu.deltasource.internship.alivingecosystem.enums;

public enum CalculationType {
	LESS_CHANCE_OF_SUCCESS_WHEN_CARNIVORES_ATTACK(1.3),

	LESS_CHANCE_OF_SUCCESS_WHEN_ATTACK_ALONE(0.5);

	public double value;

	CalculationType(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}
}
