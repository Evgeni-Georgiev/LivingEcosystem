package eu.deltasource.internship.alivingecosystem.model.animalgroup;

import eu.deltasource.internship.alivingecosystem.model.AnimalGroup;
import eu.deltasource.internship.alivingecosystem.model.animals.Herbivore;

import java.util.List;
import java.util.UUID;

public class HerbivoreGroup extends AnimalGroup<Herbivore> {

    public HerbivoreGroup(String nameGroup, Herbivore sampleAnimal, int numberOfAnimals) {
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
    public List<Herbivore> getAnimals() {
        return animals;
    }

    @Override
    public Herbivore getSampleAnimal() {
        return sampleAnimal;
    }

    @Override
    public String toString() {
        return String.format("""
                    %n HerbivoreGroup =>
                     Id: %s
                     Name of Group: %s
                     """, id, nameGroup);
    }
}
