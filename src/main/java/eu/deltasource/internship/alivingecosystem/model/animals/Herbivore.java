package eu.deltasource.internship.alivingecosystem.model.animals;

import eu.deltasource.internship.alivingecosystem.enums.HabitatType;
import eu.deltasource.internship.alivingecosystem.enums.LivingType;
import eu.deltasource.internship.alivingecosystem.model.Animal;

public class Herbivore extends Animal {

	private static int nextId = 1;

    private double escapePoints;

    public Herbivore(String name, HabitatType habitat, int age, int maxAge, int weight, LivingType livingType, int reproductionRate, int originalReproductionRate, double escapePoints) {
        super(name, habitat, age, maxAge, weight, livingType, reproductionRate, originalReproductionRate);
        this.escapePoints = escapePoints;
		this.originalReproductionRate = reproductionRate;
        this.id = nextId++;
    }

    public double getEscapePoints() {
        return escapePoints;
    }

    public void setEscapePoints(double escapePoints) {
        this.escapePoints = escapePoints;
    }

    @Override
    public String toString() {
        return String.format("""
                    %n Herbivore =>
                     id: %s
                     groupId: %s
                     Name: %s
                     Age: %s
                     Max Age: %s
                     Weight: %s
                     Habitat: %s
                     Living Type: %s
                     Reproduction Rate: %s
                     Original Reproduction Rate: %s
                     Escape Points: %s %n""",
            id, groupId, getName(), getAge(), getMaxAge(), getWeight(), getHabitat(), getLivingType(), getReproductionRate(), originalReproductionRate, escapePoints
        );
    }

}
