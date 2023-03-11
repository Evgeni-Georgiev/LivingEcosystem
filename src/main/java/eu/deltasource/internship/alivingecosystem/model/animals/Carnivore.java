package eu.deltasource.internship.alivingecosystem.model.animals;

import eu.deltasource.internship.alivingecosystem.enums.HabitatType;
import eu.deltasource.internship.alivingecosystem.enums.LivingType;
import eu.deltasource.internship.alivingecosystem.model.Animal;

import java.util.UUID;

public class Carnivore extends Animal {

    private double attackPoints;

	private int hungerLevel;

	private final int hungerRate;

    public Carnivore(String name, HabitatType habitat, int age, int maxAge, int weight, LivingType livingType, int reproductionRate, int originalReproductionRate, double attackPoints, int hungerLevel, int hungerRate) {
        super(name, habitat, age, maxAge, weight, livingType, reproductionRate, originalReproductionRate);
        this.attackPoints = attackPoints;
		this.hungerLevel = hungerLevel;
		this.originalReproductionRate = reproductionRate;
		this.hungerRate = hungerRate;
        this.id = UUID.randomUUID();
    }

    public double getAttackPoints() {
        return attackPoints;
    }

    public void setAttackPoints(double attackPoints) {
        this.attackPoints = attackPoints;
    }

	public int getHungerLevel() {
		return hungerLevel;
	}

	public void setHungerLevel(int hungerLevel) {
		this.hungerLevel = hungerLevel;
	}

	public int getHungerRate() {
		return hungerRate;
	}

	@Override
    public String toString() {
        return String.format("""
                    %n Carnivore =>
                     id: %s
                     GroupId: %s
                     Name: %s
                     Age: %s
                     Max Age: %s
                     Weight: %s
                     Habitat: %s
                     Living Type: %s
                     Reproduction Rate: %s
                     Original Reproduction Rate: %s
                     Attack Points: %s
                     Hunger Level: %s
                     """,
            id, groupId, getName(), getAge(), getMaxAge(), getWeight(), getHabitat(), getLivingType(), getReproductionRate(), originalReproductionRate, attackPoints, hungerLevel
        );
    }
}
