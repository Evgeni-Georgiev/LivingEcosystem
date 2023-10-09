package eu.deltasource.internship.alivingecosystem.model.animalgroup;

import eu.deltasource.internship.alivingecosystem.model.AnimalGroup;
import eu.deltasource.internship.alivingecosystem.model.animals.Herbivore;

import java.util.List;

public class HerbivoreGroup extends AnimalGroup<Herbivore> {

	private static int idCounter = 1;

    private final int id;

    public HerbivoreGroup(String nameGroup, Herbivore sampleAnimal, int numberOfAnimals) {
        super(nameGroup, sampleAnimal, numberOfAnimals);
        this.id = idCounter++;
    }

    @Override
    public int getId() {
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
