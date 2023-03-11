package eu.deltasource.internship.alivingecosystem.model.animalgroup;

import eu.deltasource.internship.alivingecosystem.model.AnimalGroup;
import eu.deltasource.internship.alivingecosystem.model.animals.Carnivore;

import java.util.List;
import java.util.UUID;

public class CarnivoreGroup extends AnimalGroup<Carnivore> {

    public CarnivoreGroup(String nameGroup, Carnivore sampleAnimal, int numberOfAnimals) {
        super(nameGroup, sampleAnimal, numberOfAnimals);
        this.id = UUID.randomUUID();
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getNameGroup() {
        return nameGroup;
    }

    @Override
    public List<Carnivore> getAnimals() {
        return animals;
    }

    public Carnivore getSampleAnimal() {
        return sampleAnimal;
    }

    public double getGroupAttackPoints() {
        return animals.stream()
            .mapToDouble(Carnivore::getAttackPoints)
            .sum();
    }

    @Override
    public String toString() {
        return String.format("""
                    %n CarnivoreGroup =>
                     Id: %s
                     Name of Group: %s
                     Members of the group: %s
                     Attack points of the group: %s
                     """, id, nameGroup, animals, getGroupAttackPoints()
        );
    }
}
