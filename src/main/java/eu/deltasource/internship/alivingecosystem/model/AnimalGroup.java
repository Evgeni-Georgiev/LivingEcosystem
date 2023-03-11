package eu.deltasource.internship.alivingecosystem.model;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AnimalGroup<T extends Animal> {

    protected UUID id;

    protected int numberOfAnimals;

	protected String nameGroup;

    protected T sampleAnimal;

    protected List<T> animals; // keeps single list as a model, the groups of animals is kept in repository

    public AnimalGroup(String nameGroup, T sampleAnimal, int numberOfAnimals) {
        animals = new ArrayList<>();
        this.nameGroup = nameGroup;
        this.sampleAnimal = sampleAnimal;
        this.numberOfAnimals = numberOfAnimals;
    }

    public abstract UUID getId();

    public abstract String getNameGroup();

    public abstract List<T> getAnimals();

    public abstract T getSampleAnimal();

    @Override
    public String toString() {
        return "AnimalGroup{" +
//            "id=" + id +
            "Name of the group of these animals: " + nameGroup +
            "animals=" + animals +
            "sampleAnimal=" + sampleAnimal.getName() +
            '}';
    }
}
